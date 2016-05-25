#!/usr/bin/env python2

import re
import sys


def solve(words, currentLen, used, targetLength, results):
    for i in xrange(1, targetLength - currentLen + 1):
        for word in words.get(i, []):
            if word in used:
                continue
            newLen = currentLen + i
            if i != targetLength - currentLen:
                solve(words, newLen, used + [word], targetLength, results)
            else:
                results[0]+=1


def main():
    words = {}
    if len(sys.argv) == 1:
        letters = 'abcdef01'
        targetLength = 8
    else:
        letters = sys.argv[1].replace('0', 'o').replace('1', 'il')
        targetLength = int(sys.argv[2])
    m = re.compile(r'^[' + letters + ']*$')
    for word in open('/usr/share/dict/words'):
        word = word.strip()
        if len(word) > 2 and m.match(word):
            if word in ['aaa', 'aba', 'abc']:
                continue
            if word not in words.get(len(word), []):
                words.setdefault(len(word), []).append(word)
    words[1] = ['a']
    results = [0]
    solve(words, 0, [], targetLength, results)
    print "Total:", results[0]
    import resource
    print resource.getrusage(resource.RUSAGE_SELF).ru_maxrss, "KB"

if __name__ == "__main__":
    main()
