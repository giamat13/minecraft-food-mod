"""Generate 16x16 pixel-art textures for the Food By Giamat mod.

Run once: python gen_textures.py
Writes PNGs into src/main/resources/assets/food-by-giamat/textures/{item,block}.
chocolate.png is left untouched (it already has real art).
"""
import os, random
from PIL import Image, ImageDraw

BASE = os.path.join("src", "main", "resources", "assets", "food-by-giamat", "textures")
ITEM = os.path.join(BASE, "item")
BLOCK = os.path.join(BASE, "block")


def new():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    return img, ImageDraw.Draw(img)


def shade(c, f):
    """Multiply RGB by f (keep alpha)."""
    r, g, b = c[:3]
    a = c[3] if len(c) > 3 else 255
    return (max(0, min(255, int(r * f))), max(0, min(255, int(g * f))),
            max(0, min(255, int(b * f))), a)


def save(img, folder, name):
    img.save(os.path.join(folder, name + ".png"))


def ell(d, box, fill, outline=None, w=1):
    d.ellipse(box, fill=fill, outline=outline, width=w)


# ----- individual items -------------------------------------------------

def banana():
    img, d = new()
    y = (235, 205, 60, 255)
    yd = shade(y, 0.7)
    body = [(4, 12), (3, 9), (5, 6), (8, 4), (11, 3), (13, 4),
            (13, 6), (10, 7), (8, 8), (6, 10), (5, 12)]
    d.polygon(body, fill=y, outline=yd)
    # tips
    d.point([(13, 3), (4, 13)], fill=(90, 60, 25, 255))
    # highlight
    d.line([(6, 6), (10, 5)], fill=shade(y, 1.15))
    return img


def gummy(color):
    img, d = new()
    o = shade(color, 0.65)
    ell(d, [4, 2, 7, 5], color, o)      # ear L
    ell(d, [9, 2, 12, 5], color, o)     # ear R
    ell(d, [4, 3, 12, 9], color, o)     # head
    ell(d, [3, 7, 13, 15], color, o)    # body
    ell(d, [2, 8, 5, 12], color, o)     # arm L
    ell(d, [11, 8, 14, 12], color, o)   # arm R
    # eyes + shine
    d.point([(6, 5), (9, 5)], fill=(40, 25, 25, 255))
    d.point([(6, 9), (7, 9)], fill=shade(color, 1.4))
    return img


def gummy_schnitzel():
    img = schnitzel()
    d = ImageDraw.Draw(img)
    # candy shine streak
    d.line([(5, 6), (9, 5)], fill=(255, 255, 255, 200))
    d.point([(11, 8)], fill=(255, 255, 255, 220))
    return img


def breadcrumbs():
    img, d = new()
    tan = (214, 170, 110, 255)
    random.seed(1)
    for _ in range(46):
        x, yv = random.randint(2, 13), random.randint(7, 14)
        d.point([(x, yv)], fill=shade(tan, random.uniform(0.75, 1.1)))
    return img


def _nuggets(base):
    img, d = new()
    o = shade(base, 0.65)
    ell(d, [2, 6, 9, 12], base, o)
    ell(d, [7, 4, 14, 11], base, o)
    ell(d, [4, 9, 11, 14], base, o)
    return img, d


def chicken_nuggets():
    img, d = _nuggets((226, 168, 78, 255))
    return img


def chicken_nuggets_breadcrumbs():
    img, d = _nuggets((222, 158, 70, 255))
    random.seed(3)
    for _ in range(30):
        d.point([(random.randint(3, 12), random.randint(6, 13))],
                fill=(150, 100, 45, 255))
    return img


def unbaked_nuggets():
    img, d = _nuggets((232, 214, 178, 255))
    return img


def unbaked_nuggets_bc():
    img, d = _nuggets((228, 210, 174, 255))
    random.seed(4)
    for _ in range(26):
        d.point([(random.randint(3, 12), random.randint(6, 13))],
                fill=(196, 176, 140, 255))
    return img


def schnitzel(base=(206, 150, 66, 255)):
    img, d = new()
    o = shade(base, 0.62)
    d.polygon([(3, 8), (5, 4), (9, 3), (13, 5), (14, 9),
               (12, 13), (7, 14), (3, 11)], fill=base, outline=o)
    random.seed(5)
    for _ in range(22):
        d.point([(random.randint(4, 12), random.randint(4, 12))],
                fill=shade(base, random.uniform(0.8, 1.12)))
    return img


def unbaked_schnitzel():
    img = schnitzel((224, 198, 156, 255))
    return img


def cake(base, frosting, cherry=(200, 40, 40, 255)):
    img, d = new()
    o = shade(base, 0.7)
    # cake body (cylinder)
    d.rectangle([3, 7, 12, 13], fill=base, outline=o)
    d.ellipse([3, 11, 12, 14], fill=base, outline=o)
    # frosting top
    d.ellipse([3, 4, 12, 8], fill=frosting, outline=shade(frosting, 0.7))
    # cherry
    d.ellipse([7, 2, 10, 5], fill=cherry, outline=shade(cherry, 0.6))
    return img


def cursed_cake():
    return cake((70, 40, 95, 255), (120, 60, 150, 255), (150, 20, 60, 255))


def unbaked_cake():
    return cake((226, 206, 162, 255), (236, 222, 188, 255), (210, 120, 120, 255))


def unbaked_cake_cursed():
    return cake((196, 178, 170, 255), (180, 150, 175, 255), (120, 40, 70, 255))


def dough():
    img, d = new()
    base = (232, 214, 176, 255)
    ell(d, [3, 5, 13, 14], base, shade(base, 0.75))
    ell(d, [5, 6, 9, 9], shade(base, 1.1), None)
    return img


def flour():
    img, d = new()
    sack = (236, 230, 214, 255)
    d.polygon([(4, 13), (3, 8), (5, 6), (11, 6), (13, 8), (12, 13)],
              fill=sack, outline=shade(sack, 0.72))
    # tied top
    d.rectangle([6, 4, 9, 7], fill=shade(sack, 0.9), outline=shade(sack, 0.7))
    # little wheat mark
    d.line([(8, 9), (8, 12)], fill=(196, 160, 90, 255))
    return img


def gelatin():
    img, d = new()
    base = (120, 200, 235, 170)
    d.polygon([(4, 6), (8, 4), (12, 6), (12, 12), (8, 14), (4, 12)],
              fill=base, outline=(80, 150, 190, 230))
    # top face
    d.polygon([(4, 6), (8, 4), (12, 6), (8, 8)], fill=(170, 225, 245, 190))
    d.point([(6, 9), (7, 9)], fill=(255, 255, 255, 220))
    return img


def hamburger():
    img, d = new()
    bun = (214, 158, 78, 255)
    bunl = (228, 178, 100, 255)
    # top bun
    d.pieslice([2, 2, 13, 11], 180, 360, fill=bun, outline=shade(bun, 0.7))
    d.rectangle([2, 6, 13, 7], fill=bun, outline=shade(bun, 0.7))
    # sesame
    d.point([(5, 5), (8, 4), (10, 6)], fill=bunl)
    # lettuce
    d.line([(2, 8), (13, 8)], fill=(110, 185, 80, 255))
    d.point([(3, 9), (7, 9), (11, 9)], fill=(90, 165, 70, 255))
    # patty
    d.rectangle([2, 9, 13, 11], fill=(110, 66, 40, 255))
    # bottom bun
    d.pieslice([2, 9, 13, 14], 0, 180, fill=bun, outline=shade(bun, 0.7))
    return img


def melted_chocolate():
    img, d = new()
    bowl = (210, 210, 215, 255)
    d.ellipse([2, 9, 13, 14], fill=bowl, outline=shade(bowl, 0.7))
    choc = (95, 55, 25, 255)
    d.ellipse([3, 5, 12, 11], fill=choc, outline=shade(choc, 0.7))
    d.line([(5, 7), (9, 6)], fill=shade(choc, 1.4))  # glossy swirl
    return img


def pita(base=(220, 188, 130, 255)):
    img, d = new()
    d.ellipse([2, 3, 13, 14], fill=base, outline=shade(base, 0.7))
    # pocket slit
    d.arc([4, 5, 11, 11], 200, 340, fill=shade(base, 0.6))
    return img


def unbaked_pita():
    return pita((232, 214, 178, 255))


def unbaked_bread():
    img, d = new()
    base = (228, 208, 168, 255)
    d.rounded_rectangle([2, 5, 13, 12], radius=3, fill=base,
                        outline=shade(base, 0.72))
    # scoring lines
    for x in (5, 8, 11):
        d.line([(x, 6), (x - 1, 11)], fill=shade(base, 0.85))
    return img


def salt():
    img, d = new()
    # little pile
    d.polygon([(3, 13), (8, 6), (13, 13)], fill=(245, 245, 248, 255),
              outline=(205, 205, 215, 255))
    random.seed(7)
    for _ in range(14):
        d.point([(random.randint(4, 12), random.randint(8, 12))],
                fill=(210, 210, 220, 255))
    # sparkles
    d.point([(8, 5), (5, 9), (11, 9)], fill=(255, 255, 255, 255))
    return img


def sausage(base=(150, 70, 55, 255)):
    img, d = new()
    o = shade(base, 0.65)
    # curved link
    d.line([(3, 11), (5, 6), (9, 4), (12, 6), (12, 10)], fill=base, width=4,
           joint="curve")
    d.line([(3, 11), (5, 6), (9, 4), (12, 6), (12, 10)], fill=o, width=4,
           joint="curve")
    d.line([(3, 11), (5, 7), (9, 5), (12, 7)], fill=base, width=3,
           joint="curve")
    # tied ends
    d.point([(3, 11), (2, 11), (12, 10), (13, 11)], fill=(90, 45, 35, 255))
    return img


def unbaked_sausage():
    return sausage((218, 150, 150, 255))


def sausage_in_bun():
    img, d = new()
    bun = (220, 168, 90, 255)
    d.rounded_rectangle([2, 6, 14, 11], radius=3, fill=bun,
                        outline=shade(bun, 0.7))
    # sausage
    d.rounded_rectangle([3, 7, 13, 9], radius=1, fill=(160, 75, 55, 255),
                        outline=(110, 50, 40, 255))
    # mustard zigzag
    d.line([(4, 8), (6, 7), (8, 8), (10, 7), (12, 8)], fill=(240, 200, 40, 255))
    return img


def shoko():
    img, d = new()
    glass = (225, 235, 245, 110)
    d.polygon([(4, 4), (12, 4), (11, 14), (5, 14)], fill=glass,
              outline=(170, 190, 205, 230))
    # chocolate milk
    d.polygon([(5, 7), (11, 7), (10, 13), (6, 13)], fill=(110, 70, 40, 255))
    d.line([(6, 8), (9, 8)], fill=(150, 100, 60, 255))
    # straw
    d.line([(10, 2), (8, 9)], fill=(220, 60, 70, 255), width=1)
    return img


def strainer():
    img, d = new()
    metal = (175, 178, 185, 255)
    o = shade(metal, 0.65)
    # bowl
    d.pieslice([2, 4, 12, 14], 0, 180, fill=metal, outline=o)
    d.line([(2, 9), (12, 9)], fill=o)
    # mesh holes
    for x in range(4, 11, 2):
        for yv in range(10, 13, 2):
            d.point([(x, yv)], fill=o)
    # handle
    d.line([(11, 7), (15, 4)], fill=metal, width=2)
    d.line([(11, 7), (15, 4)], fill=o, width=1)
    return img


def unbaked_cookie():
    img, d = new()
    base = (224, 200, 158, 255)
    d.ellipse([2, 3, 13, 14], fill=base, outline=shade(base, 0.72))
    # raw choc chips
    d.point([(5, 6), (9, 5), (10, 9), (6, 10), (8, 11)],
            fill=(110, 75, 50, 255))
    return img


def chili_pepper():
    img, d = new()
    red = (200, 35, 30, 255)
    rd = shade(red, 0.6)
    # curved pepper pod
    body = [(5, 14), (4, 11), (5, 8), (7, 6), (10, 5), (12, 6),
            (11, 8), (9, 9), (7, 11), (6, 13)]
    d.polygon(body, fill=red, outline=rd)
    d.line([(7, 9), (10, 7)], fill=shade(red, 1.3))  # highlight
    # green stem
    d.line([(12, 6), (13, 3)], fill=(70, 150, 55, 255), width=2)
    d.point([(13, 2), (14, 3)], fill=(90, 170, 70, 255))
    return img


def chili_pepper_bush():
    """Cross-model texture: leafy clump with a couple of red peppers."""
    img, d = new()
    g = (70, 130, 55, 255)
    gd = shade(g, 0.7)
    gl = shade(g, 1.25)
    # foliage clumps
    ell(d, [2, 7, 8, 14], g, gd)
    ell(d, [7, 6, 13, 13], g, gd)
    ell(d, [5, 4, 11, 10], g, gd)
    # a few stems up from the ground
    d.line([(8, 15), (8, 9)], fill=gd)
    d.line([(5, 15), (6, 11)], fill=gd)
    # leaf highlights
    random.seed(21)
    for _ in range(10):
        d.point([(random.randint(3, 12), random.randint(5, 13))], fill=gl)
    # hanging chili peppers
    red = (200, 35, 30, 255)
    d.line([(5, 10), (4, 13)], fill=red, width=1)
    d.point([(4, 14)], fill=shade(red, 0.7))
    d.line([(11, 9), (12, 12)], fill=red, width=1)
    d.point([(12, 13)], fill=shade(red, 0.7))
    return img


def banana_leaves_block():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    d = ImageDraw.Draw(img)
    random.seed(11)
    greens = [(56, 110, 40), (66, 128, 48), (78, 142, 56),
              (48, 96, 36), (88, 156, 64)]
    for x in range(16):
        for yv in range(16):
            c = random.choice(greens)
            d.point([(x, yv)], fill=(c[0], c[1], c[2], 255))
    # a couple of darker veins
    for x in range(16):
        d.point([(x, (x // 2) % 16)], fill=(40, 80, 30, 255))
    return img


GENERATORS = {
    "banana": banana,
    "chili_pepper": chili_pepper,
    "breadcrumbs": breadcrumbs,
    "chicken_nuggets": chicken_nuggets,
    "chicken_nuggets_breadcrumbs": chicken_nuggets_breadcrumbs,
    "cursed_cake": cursed_cake,
    "dough": dough,
    "flour": flour,
    "gelatin": gelatin,
    "gummy_candy_apple": lambda: gummy((200, 50, 50, 220)),
    "gummy_candy_carrot": lambda: gummy((232, 140, 40, 220)),
    "gummy_candy_sweet_berries": lambda: gummy((120, 70, 170, 220)),
    "gummy_candy_watermelon": lambda: gummy((90, 180, 80, 220)),
    "gummy_schnitzel": gummy_schnitzel,
    "hamburger": hamburger,
    "melted_chocolate": melted_chocolate,
    "pita": pita,
    "salt": salt,
    "sausage": sausage,
    "sausage_in_bun": sausage_in_bun,
    "schnitzel": schnitzel,
    "shoko": shoko,
    "strainer": strainer,
    "unbaked_bread": unbaked_bread,
    "unbaked_cake": unbaked_cake,
    "unbaked_cake_cursed": unbaked_cake_cursed,
    "unbaked_chicken_nuggets": unbaked_nuggets,
    "unbaked_chicken_nuggets_breadcrumbs": unbaked_nuggets_bc,
    "unbaked_cookie": unbaked_cookie,
    "unbaked_pita": unbaked_pita,
    "unbaked_sausage": unbaked_sausage,
    "unbaked_schnitzel": unbaked_schnitzel,
}


def main():
    for name, fn in GENERATORS.items():
        save(fn(), ITEM, name)
    save(banana_leaves_block(), BLOCK, "banana_leaves")
    save(chili_pepper_bush(), BLOCK, "chili_pepper_bush")
    print("generated", len(GENERATORS) + 2, "textures")


if __name__ == "__main__":
    main()
