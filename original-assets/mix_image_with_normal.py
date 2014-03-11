from PIL import Image
im = Image.new("RGBA", (1024, 1024), "white")
im1 = Image.open("wood.png")
im2 = Image.open("wood_normal.png")

for x in range(0, 512):
    for y in range(0, 1024):
        pixel = im1.getpixel((x, y))
        # Make Green the alpha
        im.putpixel((x, y), (pixel[0], pixel[1], pixel[2], pixel[3]))

        pixel2 = im2.getpixel((x, y))
        im.putpixel((512 + x, y), (pixel2[0], pixel2[1], pixel2[2], pixel2[3]))

im.save("wood_with_normal.png", "png")
