#include <iostream>
#include <string>
#include <vector>

using namespace std;

int main() {
    int border1, border2;
    scanf("%d %d", &border1, &border2);

    if (border1 > 99 || border1 < 10 || border2 > 99 || border2 < 10) {
        cout << "Incorrect borders";
        exit(-1);
    }

    vector<int> numbersToPrint;
    int oddCount = 0;
    for (int i = border1; i <= border2; i++) {
        if (abs(i % 10 - i / 10) == 1) {
            numbersToPrint.push_back(i);
            cout << i << ' ';

            if (i % 2 == 1) {
                oddCount++;
            }
        }
    }
    cout << endl;

    cout << "Printed number: " << numbersToPrint.size() << endl;
    cout << "Odd count: " << oddCount << endl;
    cout << "Even count: " << numbersToPrint.size() - oddCount << endl;

    return 0;
}
