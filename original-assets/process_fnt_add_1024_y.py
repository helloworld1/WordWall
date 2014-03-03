import re
f = open("serif3.fnt")
lines = f.readlines()
for line in lines:
    m = re.search(r"y=\d+", line)
    if m != None:
        y = m.group(0).lstrip("y=")
        yint = int(y)
        yint += 1024
        line=re.sub(r"y=\d+", "y=" + str(yint), line)
    print(line, end="")
