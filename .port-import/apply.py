"""Apply a hash-verified, exact-base source-tree delta (not executable payload code)."""
from pathlib import Path
import sys,subprocess,json,lzma,base64,hashlib

def git(*args):return subprocess.check_output(['git',*args]).decode().strip()
root=Path.cwd().resolve()
manifest=json.loads(lzma.decompress(Path(sys.argv[1]).read_bytes()))
if git('rev-parse','HEAD^{tree}')!=manifest['base_tree']:
 raise SystemExit('Refusing to apply: base source tree has changed')
if git('status','--porcelain'):
 raise SystemExit('Refusing to apply to a dirty checkout')
def path(value):
 p=Path(value)
 if p.is_absolute() or '..' in p.parts or '.git' in p.parts or not p.parts:
  raise ValueError('Unsafe path '+str(p))
 if not (root/p).resolve().is_relative_to(root):raise ValueError('Path escapes checkout')
 return root/p
pending={};remove=set()
for entry in manifest['files']:
 name=entry['p'];p=path(name)
 if name in pending or name in remove:raise ValueError('Duplicate target '+name)
 if entry.get('d'):
  if not p.is_file():raise ValueError('Missing deletion source '+name)
  remove.add(name);continue
 if 'n' in entry:
  if p.exists():raise ValueError('Addition already exists '+name)
  content=entry['n'].encode('utf-8')
 else:
  source=entry.get('b',name);content=path(source).read_bytes()
  if source!=name:remove.add(source)
  if 'e' in entry:
   lines=content.decode('utf-8').splitlines(keepends=True);edits=entry['e'];end=0
   for start,stop,value in edits:
    if not (end<=start<=stop<=len(lines)):raise ValueError('Overlapping/invalid edits '+name)
    end=stop
   for start,stop,value in reversed(edits):lines[start:stop]=[value]
   content=''.join(lines).encode('utf-8')
 pending[name]=content
for name in remove:path(name).unlink()
for name,content in pending.items():
 p=path(name);p.parent.mkdir(parents=True,exist_ok=True);p.write_bytes(content)
subprocess.run(['git','add','-A'],check=True)
subprocess.run(['git','diff','--cached','--check'],check=True)
actual=git('write-tree')
if actual!=manifest['tree']:raise SystemExit('Output tree mismatch: '+actual+' != '+manifest['tree'])
print('Verified exact source tree:',actual,';',len(pending),'writes;',len(remove),'removed paths')
