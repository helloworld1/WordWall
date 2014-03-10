from PIL import Image
im = Image.new("RGBA", (2048, 2048), "white")
im1 = Image.open("chinese.png")
im2 = Image.open("ascii.png")

line = 1704

for x in range(0, 2048):
    for y in range(0, line):
        pixel = im1.getpixel((x, y))
        # Make Green the alpha
        im.putpixel((x, y), (pixel[0], pixel[3], pixel[0], pixel[3]))

for x in range(0, 2048):
    for y in range(line, 2048):
        pixel = im2.getpixel((x, y - line))
        # Make Green the alpha
        im.putpixel((x, y), (pixel[0], pixel[3], pixel[0], pixel[3]))

im.save("mixed.png", "png")
