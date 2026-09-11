"""Source/data format contracts, not a replacement for a real resource reload."""
from pathlib import Path
import json
import unittest

ROOT = Path(__file__).resolve().parents[2]
DATA = [ROOT / 'src/main/resources/data', ROOT / 'src/generated/resources/data']

class ResourceContracts(unittest.TestCase):
    def test_asset_loaders_and_face_data_use_neoforge(self):
        for folder in ['src/main/resources/assets', 'src/generated/resources/assets']:
            for path in (ROOT / folder).rglob('*.json'):
                text = path.read_text()
                self.assertNotIn('"forge:', text, str(path))
                self.assertNotIn('"forge_data"', text, str(path))

    def test_consummate_nest_variants_match_runtime_states(self):
        path = ROOT / 'src/main/resources/assets/clanginghowl/blockstates/consummate_nest.json'
        self.assertEqual({'consummate_nest_state=' + value for value in
                          ['inactive', 'active', 'ejecting_reward', 'cooldown']},
                         set(json.loads(path.read_text())['variants']))

    def test_geckolib_geometry_uses_supported_version(self):
        for path in (ROOT / 'src/main/resources/assets/clanginghowl/geo').glob('*.json'):
            self.assertEqual('1.12.0', json.loads(path.read_text())['format_version'], str(path))

    def test_no_legacy_data_directories(self):
        obsolete = {'advancements', 'recipes', 'loot_tables', 'structures', 'predicates', 'functions', 'item_modifiers'}
        obsolete_tags = {'items', 'blocks', 'entity_types', 'fluids', 'game_events', 'functions'}
        for root in DATA:
            for namespace in root.iterdir():
                if not namespace.is_dir():
                    continue
                self.assertFalse(obsolete.intersection(p.name for p in namespace.iterdir()))
                tag_root = namespace / 'tags'
                if tag_root.exists():
                    self.assertFalse(obsolete_tags.intersection(p.name for p in tag_root.iterdir()))

    def test_recipe_results_are_item_stacks(self):
        for root in DATA:
            for path in root.glob('*/recipe/**/*.json'):
                value = json.loads(path.read_text())
                if 'result' in value:
                    self.assertIsInstance(value['result'], dict, str(path))
                    self.assertIn('id', value['result'], str(path))
                    self.assertNotIn('item', value['result'], str(path))

    def test_no_obsolete_forge_data_types_or_tags(self):
        for root in DATA:
            for path in root.rglob('*.json'):
                text = path.read_text()
                json.loads(text)
                self.assertNotIn('forge:', text.replace('neoforge:', ''), str(path))
                self.assertNotIn('minecraft:set_nbt', text, str(path))

    def test_all_structures_survive_directory_migration(self):
        structures = ROOT / 'src/main/resources/data/clanginghowl/structure'
        self.assertEqual(14, len(list(structures.glob('*.nbt'))))

    def test_all_twelve_enchantments_have_definitions(self):
        definitions = list((DATA[0] / 'clanginghowl/enchantment').glob('*.json'))
        self.assertEqual(12, len(definitions))
        for path in definitions:
            value = json.loads(path.read_text())
            self.assertIn('supported_items', value)
            self.assertGreaterEqual(value['max_level'], 1)
            self.assertIn('effects', value)

    def test_enum_extensions_preserve_all_six_arm_poses(self):
        meta = ROOT / 'src/main/resources/META-INF/enumextensions.json'
        entries = json.loads(meta.read_text())['entries']
        self.assertEqual({'CLANGINGHOWL_CHAINSAW', 'CLANGINGHOWL_IDLE_SAW', 'CLANGINGHOWL_DRILL', 'CLANGINGHOWL_IDLE_DRILL', 'CLANGINGHOWL_FLAME', 'CLANGINGHOWL_IDLE_FLAME'}, {e['name'] for e in entries})
        self.assertTrue(all(e['enum'] == 'net/minecraft/client/model/HumanoidModel$ArmPose' for e in entries))
        self.assertIn('enumExtensions="META-INF/enumextensions.json"', (ROOT / 'src/main/templates/META-INF/neoforge.mods.toml').read_text())

if __name__ == '__main__':
    unittest.main(verbosity=2)
