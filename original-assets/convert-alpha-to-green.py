from PIL import Image
im = Image.open("mixed.png")
for x in range(0, 2048):
    for y in range(0, 2048):
        pixel = im.getpixel((x, y))
        # Make Green the alpha
        im.putpixel((x, y), (pixel[0], pixel[3], pixel[0], pixel[3]))

im.save("mixed_with_alpha.png", "png")
