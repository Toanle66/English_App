package cmdline_package;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {

    public void showAllWords() {
        // sắp xếp theo alphabet
        Words.sort((obj1, obj2) -> {
            String str1 = obj1.getWord_target();
            String str2 = obj2.getWord_target();
            return str1.compareToIgnoreCase(str2);
        });
        // out
        System.out.printf("%-5s | %-5s | %-20s | %-15s | %-40s | %-20s | %s%n", "STT", "ID", "Tiếng Anh", "Loại từ", "Nghĩa", "Phát âm", "Ví dụ");
        for (int i = 0; i < Words.size(); i++) {
            Word word_ = Words.get(i);
            System.out.printf("%-5d | %-5d | %-20s | %-15s | %-40s | %-20s | %s%n", i + 1, word_.getId(), word_.getWord_target(), word_.getWord_type(), word_.getWord_explain(), word_.getPronunciation(), word_.getExample());
        }
    }

    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }

    private void waitInput() {
        System.out.println("Nhấn ENTER để tiếp tục.");
        scan.nextLine();
    }

    private byte getChoiceInput() {
        String input = scan.nextLine();
        if (input.matches("[0-9]")) { // check if the input is 0-9
            return Byte.parseByte(input);
        } else {
            return 10; // out of range
        }
    }

    public void dictionaryAdvanced() {
        boolean running = true;
        do {
            System.out.print("""
                    Welcome to Dictionary Ultra Pro Max!
                    [0] Exit
                    [1] Add
                    [2] Remove
                    [3] Update
                    [4] Display
                    [5] Lookup
                    [6] Search
                    [7] Game
                    [8] Import from file
                    [9] Export to file
                    Hãy nhập hành động:
                    """);
            byte choice = getChoiceInput();
            switch (choice) {
                case 0 -> { // exit
                    System.out.println("Cảm ơn bạn vì đã sử dụng ứng dụng.");
                    running = false;
                }
                case 1 -> { // add
                    this.addWord();
                    waitInput();
                }
                case 2 -> { // remove
                    this.removeWord();
                    waitInput();
                }
                case 3 -> { // update
                    this.updateWord();
                    waitInput();
                }
                case 4 -> { // display
                    this.showAllWords();
                    waitInput();
                }
                case 5 -> { //
                    this.dictionaryLookup();
                    waitInput();
                }
                case 6 -> {
                    this.dictionarySearcher();
                    waitInput();
                }
                case 7 -> {
                    DictionaryGame.play();
                    waitInput();
                }
                case 8 -> {
                    this.insertFromFile();
                    waitInput();
                }
                case 9 -> {
                    this.dictionaryExportToFile();
                    waitInput();
                }
                default -> {
                    System.out.println("Hành động không được hỗ trợ.");
                    waitInput();
                }
            }
        } while (running);
    }
}
