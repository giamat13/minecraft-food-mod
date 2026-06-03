"""Generate the new textures (grape, wine, sweet dough, challah, grape bush,
torah stand) and the rabbi_house structure NBT for the Food By Giamat mod.

Run once: python gen_rabbi_assets.py
Only writes the *new* assets; existing PNGs are left untouched.
"""
import gzip
import os
import struct

from PIL import Image, ImageDraw

BASE = os.path.join("src", "main", "resources")
ASSETS = os.path.join(BASE, "assets", "food-by-giamat", "textures")
ITEM = os.path.join(ASSETS, "item")
BLOCK = os.path.join(ASSETS, "block")
STRUCT_DIR = os.path.join(BASE, "data", "food-by-giamat", "structure")
# Vanilla namespace path for villager profession textures
VILLAGER_PROF = os.path.join(BASE, "assets", "minecraft", "textures", "entity", "villager", "profession")

for d in (ITEM, BLOCK, STRUCT_DIR, VILLAGER_PROF):
    os.makedirs(d, exist_ok=True)


def new():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    return img, ImageDraw.Draw(img)


def save(img, folder, name):
    img.save(os.path.join(folder, name + ".png"))


# --------------------------------------------------------------------------
# Textures
# --------------------------------------------------------------------------

def grape():
    img, d = new()
    purple = (104, 54, 140, 255)
    dark = (74, 36, 102, 255)
    light = (150, 96, 184, 255)
    # stem
    d.line((8, 1, 8, 3), fill=(86, 56, 30, 255))
    d.line((8, 2, 10, 1), fill=(64, 110, 40, 255))
    # cluster of berries (triangle)
    centers = [
        (8, 5), (5, 7), (11, 7), (7, 9), (10, 9),
        (6, 11), (9, 11), (8, 13),
    ]
    for (cx, cy) in centers:
        d.ellipse((cx - 2, cy - 2, cx + 2, cy + 2), fill=purple, outline=dark)
        d.point((cx - 1, cy - 1), fill=light)
    save(img, ITEM, "grape")


def wine():
    img, d = new()
    glass = (220, 230, 235, 90)
    liquid = (122, 20, 40, 255)
    liquid_top = (160, 36, 60, 255)
    # bowl of the glass
    d.ellipse((4, 2, 11, 9), fill=glass, outline=(200, 210, 215, 200))
    # wine inside
    d.ellipse((5, 4, 10, 9), fill=liquid)
    d.line((5, 5, 10, 5), fill=liquid_top)
    # stem and base
    d.line((8, 9, 8, 13), fill=(210, 220, 225, 200), width=1)
    d.line((6, 14, 10, 14), fill=(210, 220, 225, 220))
    save(img, ITEM, "wine")


def sweet_dough():
    img, d = new()
    dough = (226, 200, 150, 255)
    dark = (196, 168, 118, 255)
    sugar = (255, 255, 255, 255)
    d.ellipse((3, 5, 12, 12), fill=dough, outline=dark)
    d.ellipse((5, 4, 11, 9), fill=dough, outline=dark)
    # sugar sprinkles
    for (sx, sy) in [(6, 7), (9, 8), (7, 10), (10, 6), (5, 9)]:
        d.point((sx, sy), fill=sugar)
    save(img, ITEM, "sweet_dough")


def _braid(d, baked):
    base = (210, 150, 70, 255) if baked else (228, 205, 150, 255)
    dark = (170, 110, 45, 255) if baked else (198, 172, 120, 255)
    light = (236, 190, 110, 255) if baked else (240, 222, 180, 255)
    # overall loaf
    d.rounded_rectangle((2, 5, 13, 11), radius=3, fill=base, outline=dark)
    # braid segments (diagonal strands)
    for i, x in enumerate(range(3, 13, 2)):
        d.line((x, 5, x - 2, 11), fill=dark, width=1)
        d.line((x + 1, 6, x - 1, 10), fill=light, width=1)
    return baked


def challah():
    img, d = new()
    _braid(d, True)
    # poppy seeds
    for (sx, sy) in [(5, 7), (8, 6), (10, 8), (6, 9), (9, 9)]:
        d.point((sx, sy), fill=(40, 30, 30, 255))
    save(img, ITEM, "challah")


def unbaked_challah():
    img, d = new()
    _braid(d, False)
    save(img, ITEM, "unbaked_challah")


def grape_bush():
    img, d = new()
    leaf = (60, 120, 48, 255)
    leaf_d = (40, 92, 34, 255)
    # bushy foliage
    for (cx, cy, r) in [(8, 9, 5), (5, 7, 3), (11, 8, 3), (8, 5, 3)]:
        d.ellipse((cx - r, cy - r, cx + r, cy + r), fill=leaf, outline=leaf_d)
    # grape clusters hanging
    purple = (104, 54, 140, 255)
    for (cx, cy) in [(5, 11), (10, 12), (8, 13)]:
        d.ellipse((cx - 1, cy - 1, cx + 1, cy + 1), fill=purple)
    save(img, BLOCK, "grape_bush")


def grape_bush_empty():
    img, d = new()
    leaf = (60, 120, 48, 255)
    leaf_d = (40, 92, 34, 255)
    for (cx, cy, r) in [(8, 9, 5), (5, 7, 3), (11, 8, 3), (8, 5, 3)]:
        d.ellipse((cx - r, cy - r, cx + r, cy + r), fill=leaf, outline=leaf_d)
    save(img, BLOCK, "grape_bush_empty")


def torah_stand():
    # side texture: a wooden lectern-like stand with a scroll
    img, d = new()
    wood = (150, 106, 58, 255)
    wood_d = (110, 76, 38, 255)
    wood_l = (178, 132, 78, 255)
    d.rectangle((0, 0, 15, 15), fill=wood)
    # vertical planks
    for x in (0, 5, 10, 15):
        d.line((x, 0, x, 15), fill=wood_d)
    d.line((2, 0, 2, 15), fill=wood_l)
    # a small Star-of-David-ish emblem in gold
    gold = (224, 188, 70, 255)
    d.line((8, 4, 6, 8), fill=gold)
    d.line((6, 8, 10, 8), fill=gold)
    d.line((10, 8, 8, 4), fill=gold)
    d.line((8, 10, 6, 6), fill=gold)
    d.line((6, 6, 10, 6), fill=gold)
    d.line((10, 6, 8, 10), fill=gold)
    save(img, BLOCK, "torah_stand")


def torah_stand_top():
    img, d = new()
    wood = (164, 118, 66, 255)
    wood_d = (120, 84, 44, 255)
    d.rectangle((0, 0, 15, 15), fill=wood, outline=wood_d)
    # an open scroll on top
    parch = (232, 222, 196, 255)
    parch_d = (200, 188, 156, 255)
    d.rounded_rectangle((3, 4, 12, 11), radius=2, fill=parch, outline=parch_d)
    for y in (6, 8, 10):
        d.line((5, y, 10, y), fill=(120, 90, 60, 255))
    save(img, BLOCK, "torah_stand_top")


def rabbi_profession():
    # 64x64 villager profession overlay texture (vanilla layout)
    img = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
    d = ImageDraw.Draw(img)
    hat_dark = (30, 20, 10, 255)
    hat_mid = (50, 35, 18, 255)
    hat_brim = (40, 28, 14, 255)
    beard = (220, 210, 190, 255)
    gold = (224, 188, 70, 255)

    # Head overlay region: x=8..15, y=8..15 (front face of head)
    # Hat brim across top of head
    d.rectangle((8, 8, 15, 9), fill=hat_brim)
    # Hat body (tall black hat - streimel style)
    d.rectangle((9, 5, 14, 8), fill=hat_dark, outline=hat_mid)
    # Gold band on hat
    d.line((9, 7, 14, 7), fill=gold)

    # White beard on lower face (y=13..15 of head)
    for y in (13, 14, 15):
        d.line((9, y, 14, y), fill=beard)

    # Body overlay region: x=20..27, y=20..31 (front of body)
    # White/cream robe stripe
    robe = (240, 235, 220, 255)
    d.rectangle((20, 20, 27, 31), fill=robe)
    # Dark coat over robe (bekishe)
    coat = (35, 25, 15, 255)
    d.rectangle((20, 20, 21, 31), fill=coat)
    d.rectangle((26, 20, 27, 31), fill=coat)
    # Gold belt buckle
    d.rectangle((22, 25, 25, 27), fill=gold)

    img.save(os.path.join(VILLAGER_PROF, "rabbi.png"))


for fn in (grape, wine, sweet_dough, challah, unbaked_challah,
           grape_bush, grape_bush_empty, torah_stand, torah_stand_top,
           rabbi_profession):
    fn()
print("textures written")


# --------------------------------------------------------------------------
# Minimal NBT writer (big-endian, gzip-compressed) for the structure file
# --------------------------------------------------------------------------

TAG_END, TAG_INT, TAG_STRING, TAG_LIST, TAG_COMPOUND, TAG_DOUBLE = 0, 3, 8, 9, 10, 6


def _str(s):
    b = s.encode("utf-8")
    return struct.pack(">H", len(b)) + b


def _named(tag_type, name, payload):
    return bytes([tag_type]) + _str(name) + payload


def _int(v):
    return struct.pack(">i", v)


def _double(v):
    return struct.pack(">d", v)


def _list(elem_type, payloads):
    return bytes([elem_type]) + struct.pack(">i", len(payloads)) + b"".join(payloads)


def _compound(named_tags):
    return b"".join(named_tags) + bytes([TAG_END])


def _int_list(values):
    return _list(TAG_INT, [_int(v) for v in values])


def _double_list(values):
    return _list(TAG_DOUBLE, [_double(v) for v in values])


# ---- palette -------------------------------------------------------------
# Each palette entry: a block state (Name + optional Properties).
def palette_entry(name, properties=None):
    tags = [_named(TAG_STRING, "Name", _str(name))]
    if properties:
        prop_tags = [_named(TAG_STRING, k, _str(v)) for k, v in properties.items()]
        tags.append(_named(TAG_COMPOUND, "Properties", _compound(prop_tags)))
    return _compound(tags)


PALETTE = [
    ("minecraft:air", None),                     # 0
    ("minecraft:oak_planks", None),              # 1
    ("minecraft:oak_log", {"axis": "y"}),        # 2
    ("food-by-giamat:torah_stand", None),        # 3
    ("minecraft:bookshelf", None),               # 4
    ("minecraft:glass", None),                    # 5
    ("minecraft:white_wool", None),              # 6
]

SX, SY, SZ = 7, 5, 7


def state_at(x, y, z):
    if y == 0 or y == 4:
        return 1  # floor / flat roof
    # walls (y = 1..3)
    perimeter = x in (0, 6) or z in (0, 6)
    if perimeter:
        if x in (0, 6) and z in (0, 6):
            return 2  # corner log post
        if x == 3 and z == 0 and y in (1, 2):
            return 0  # doorway
        if y == 2 and ((z in (0, 6) and x in (2, 4)) or (x in (0, 6) and z in (2, 4))):
            return 5  # window
        return 1
    # interior
    if (x, y, z) == (3, 1, 3):
        return 3  # torah stand
    if y == 1 and (x, z) in [(1, 5), (5, 5)]:
        return 4  # bookshelves
    if y == 1 and (x, z) in [(2, 5), (4, 5)]:
        return 6  # wool seats
    return 0


def build():
    palette_payloads = [palette_entry(n, p) for (n, p) in PALETTE]

    block_payloads = []
    for y in range(SY):
        for x in range(SX):
            for z in range(SZ):
                s = state_at(x, y, z)
                block_payloads.append(_compound([
                    _named(TAG_INT, "state", _int(s)),
                    _named(TAG_LIST, "pos", _int_list([x, y, z])),
                ]))

    # rabbi villager baked into the house
    villager_nbt = _compound([
        _named(TAG_STRING, "id", _str("minecraft:villager")),
        _named(TAG_COMPOUND, "VillagerData", _compound([
            _named(TAG_STRING, "type", _str("minecraft:plains")),
            _named(TAG_STRING, "profession", _str("food-by-giamat:rabbi")),
            _named(TAG_INT, "level", _int(2)),
        ])),
        _named(TAG_INT, "PersistenceRequired", _int(1)),
    ])
    entity_payload = _compound([
        _named(TAG_LIST, "pos", _double_list([3.5, 1.0, 4.5])),
        _named(TAG_LIST, "blockPos", _int_list([3, 1, 4])),
        _named(TAG_COMPOUND, "nbt", villager_nbt),
    ])

    root_payload = _compound([
        _named(TAG_INT, "DataVersion", _int(4671)),
        _named(TAG_LIST, "size", _int_list([SX, SY, SZ])),
        _named(TAG_LIST, "palette", _list(TAG_COMPOUND, palette_payloads)),
        _named(TAG_LIST, "blocks", _list(TAG_COMPOUND, block_payloads)),
        _named(TAG_LIST, "entities", _list(TAG_COMPOUND, [entity_payload])),
    ])
    root = _named(TAG_COMPOUND, "", root_payload)

    out = os.path.join(STRUCT_DIR, "rabbi_house.nbt")
    with gzip.open(out, "wb") as f:
        f.write(root)
    print("structure written:", out, "(%d blocks)" % len(block_payloads))


build()
