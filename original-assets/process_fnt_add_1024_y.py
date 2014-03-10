import re
f = open("ascii.fnt")

y_to_add = 1704

lines = f.readlines()
for line in lines:
    m = re.search(r"y=\d+", line)
    if m != None:
        y = m.group(0).lstrip("y=")
        yint = int(y)
        yint += y_to_add 
        line=re.sub(r"y=\d+", "y=" + str(yint), line)
    print(line, end="")
