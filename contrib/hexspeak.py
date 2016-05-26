#!/usr/bin/env python2

import re
import sys


def solve_recursive_count(words, currentLen, used, targetLength, cnt):
    for i in xrange(1, targetLength - currentLen + 1):
        for word in words.get(i, []):
            if word in used:
                continue
            if i != targetLength - currentLen:
                solve_recursive_count(
                    words, currentLen + i, used + [word], targetLength, cnt)
            else:
                cnt[0] += 1


def solve_nonrecursive_count(words, targetLength):
    from collections import deque
    candidates = deque([([], 0)])
    cnt = 0
    while candidates:
        wordsSoFar, currentLen = candidates.popleft()
        if currentLen == targetLength:
            cnt += 1
        else:
            for i in xrange(1, targetLength - currentLen + 1):
                for word in words.get(i, []):
                    if word not in wordsSoFar:
                        candidates.append(
                            (wordsSoFar + [word], currentLen + i))
    return cnt


def main():
    words = {}
    if len(sys.argv) != 4:
        targetLength = 8
        letters = 'abcdef01'
        dictionaryFile = '/usr/share/dict/words'
    else:
        targetLength = int(sys.argv[1])
        letters = sys.argv[2].replace('0', 'o').replace('1', 'il')
        dictionaryFile = sys.argv[3]
    m = re.compile(r'^[' + letters + ']*$')
    for word in open(dictionaryFile):
        word = word.strip()
        if len(word) > 2 and m.match(word):
            if word in ['aaa', 'aba', 'abc']:
                continue
            if word not in words.get(len(word), []):
                words.setdefault(len(word), []).append(word)
    words[1] = ['a']
    cnt = [0]
    solve_recursive_count(words, 0, [], targetLength, cnt)
    print "Total:", cnt[0]
    #
    # Not using recursion is much slower! Apparently cache is thrashed
    #
    # results = solve_nonrecursive_count(words, targetLength)
    # print "Total:", results
    # import resource # Proof this has much more memory impact...
    # print resource.getrusage(resource.RUSAGE_SELF).ru_maxrss, "KB"

if __name__ == "__main__":
    main()
