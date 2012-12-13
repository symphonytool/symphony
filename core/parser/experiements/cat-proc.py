#!/usr/bin/python

cats = {}


class CodepointRange:
    def __init__(self, start, end):
        self.start = start
        self.end = end
    def __init__(self, single):
        self.start = single
        self.end = single

    def adjacent(self, codepoint):
        return codepoint+1==self.start or self.end+1==codepoint

    def addIfAdj(self, codepoint):
        if self.adjacent(codepoint):
            if codepoint<self.start:
                self.start = codepoint
            elif codepoint>self.end:
                self.end = codepoint
            return True
        else:
            return False

    def __str__(self):
        if self.start == self.end:
            return "'\u%04x'" % self.start
        else:
            return "'\u%04x'..'\u%04x'" % (self.start,self.end)


with open('Categories.txt') as file:
    for line in file:
        codepoint = int(line[0:4], 16)
        key = line[5:7]
        cpr = None
        if key not in cats:
            cats[key] = [CodepointRange(codepoint)]
        else:
            cpr = cats[key][-1]
            if not cpr.addIfAdj(codepoint):
                cpr = CodepointRange(codepoint)
                cats[key].append(CodepointRange(codepoint));

for key in cats:
    print "fragment\nUNICODE_%s" % key
    cps = cats[key]
    first = False    
    while cps:
        if not first:
            print "    | ",
        else:
            print "    : ",
        six = cps[0:6]
        print " | ".join(map(CodepointRange.__str__,six))
        cps = cps[6:]
    print "    ;"

