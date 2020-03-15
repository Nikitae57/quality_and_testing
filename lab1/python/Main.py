import sys


border1 = int(input())
border2 = int(input())

if len(str(border1)) != 2 or len(str(border2)) != 2:
    print("Incorrect borders")
    sys.exit(-1)

numbersToPrint = []
oddCount = 0
for i in range(border1, border2 + 1):
    if abs(i % 10 - i // 10) == 1:
        printedNumbers.append(i) 
        if i % 2 != 0:
            oddCount += 1

print(printedNumbers)
print("Printed number:", len(printedNumbers))
print("Odd number:", oddCount)
print("Even number:", len(printedNumbers) - oddCount)
