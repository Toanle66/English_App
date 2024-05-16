package cmdline_package;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;
import java.lang.String;


public class DictionaryManagement extends Dictionary {
    public static Scanner scan = new Scanner(System.in);

    public void insertFromCommandline() {
        System.out.println("Nhập số từ muốn thêm: ");
        int n; // slg từ vựng.
        String input = scan.nextLine();
        if (input.matches("[0-9]+")) {
            n = Integer.parseInt(input);
            // nhập vào n từ vựng
            for (int i = 0; i < n; i++) {
                boolean wordAlreadyExists = false;
                System.out.println(i + 1 + ".Nhập từ mới: ");
                String word_target_ = scan.nextLine();
                for (Word word : Words) {
                    if (word_target_.equals(word.getWord_target())) {
                        System.out.println("Từ đã tồn tại trong từ điển!");
                        wordAlreadyExists = true;
                        break;
                    }
                }
                if (!wordAlreadyExists) {
                    System.out.println("Nhập dạng từ của từ: ");
                    String word_type_ = scan.nextLine();

                    System.out.println("Nhập định nghĩa tiếng Việt của từ: ");
                    String word_explain_ = scan.nextLine();

                    System.out.println("Nhập cách phát âm từ: ");
                    String pronunciation_ = scan.nextLine();

                    System.out.println("Nhập ví dụ của từ: ");
                    String example_ = scan.nextLine();

                    Word temp = new Word(word_target_, word_type_, word_explain_, pronunciation_, example_);
                    temp.setId(word_count);
                    Words.add(temp); // add vào arraylist
                    word_count++;
                }
            }
        } else {
            System.out.println("Đầu vào không hợp lệ !");
        }
    }

    public void insertFromFile() {
        String filePath;
        System.out.println("Điền đường dẫn file hoặc nhấn ENTER để dùng đường dẫn mặc định.");
        filePath = scan.nextLine();
        if (filePath.isBlank()) {
            System.out.println("Không có đường dẫn được nhập, sử dụng đường dẫn mặc định...");
            filePath = "src\\resources\\dictionaries.txt";
        } else if (Files.notExists(Path.of(filePath))) {
            System.out.println("Không thể tìm thấy file từ đường dẫn đã nhập.");
            return;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
            boolean sameWord = false;
            int numberOfDuplicateWords = 0; // Số từ từ file input bị lặp/đã xuất hiện trong từ điển.
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineFromFile = line.split("\t");
                if (lineFromFile.length == 2) {
                    String wordInEnglish = lineFromFile[0];
                    String wordExplainInVn = lineFromFile[1];
                    for (Word word : Words) {
                        if (wordInEnglish.equals(word.getWord_target())) {
                            numberOfDuplicateWords++;
                            System.out.println("Từ " + wordInEnglish + " đã có trong từ điển! Đang ghi đè dữ liệu của từ...");
                            word.setWord_target(wordInEnglish);
                            word.setWord_type("temp_word_type");
                            word.setWord_explain(wordExplainInVn);
                            word.setPronunciation("temp_pronunciation");
                            word.setExample("temp_word_example");
                            sameWord = true;
                        }
                    }
                    if(!sameWord) {
                        Word word = new Word(wordInEnglish, "temp_word_type", wordExplainInVn, "temp_pronunciation", "temp_word_example");
                        word.setId(word_count);
                        word_count++;
                        Words.add(word);
                    }
                    sameWord = false;
                }
            }
            System.out.println("Số từ bị lặp trong file là: " + numberOfDuplicateWords + ".");
            System.out.println("Nhập dữ liệu thành công từ file.");
        } catch (Exception e) {
            System.out.println("Đã xảy ra sự cố.. :(");
            e.printStackTrace();
        }
    }

    public void dictionaryLookup() {
        System.out.println("Mời bạn nhập id từ muốn tra: ");
        int id;
        String input = scan.nextLine();
        if (input.matches("[0-9]+")) {
            id = Integer.parseInt(input);
            for (Word word : Words) {
                if (id == word.getId()) {
                    System.out.printf("%-3d | %-10s\t| %-15s\t| %-30s\t| %-30s\t| %s%n", word.getId(), word.getWord_target(), word.getWord_type(), word.getWord_explain(), word.getPronunciation(), word.getExample());
                }
            }
        } else {
            System.out.println("Đầu vào không hợp lệ !");
        }
    }

    public void addWord() {
        System.out.println("Mời bạn nhập một từ mới theo hướng dẫn");
        System.out.println("Nhập từ mới: ");
        String word_target = scan.nextLine();
        for (Word word : Words) { //if word already exists, exit operation
            if (word_target.equals(word.getWord_target())) {
                System.out.println("Từ này đã tồn tại trong từ điển!");
                return;
            }
        }
        System.out.println("Nhập dạng từ của từ mới: ");
        String word_type = scan.nextLine();
        System.out.println("Nhập định nghĩa tiếng Việt của từ mới: ");
        String word_explain = scan.nextLine();
        System.out.println("Nhập cách phát âm từ mới: ");
        String pronunciation = scan.nextLine();
        System.out.println("Nhập ví dụ của từ mới: ");
        String example = scan.nextLine();
        Word newWord = new Word(word_target, word_type, word_explain, pronunciation, example);
        newWord.setId(word_count);
        Words.add(newWord);
        word_count++;
    }

    public void updateWord() {
        System.out.println("""
                Hãy chọn cách thức cập nhật:\s
                [0] Theo id\s
                [1] Theo từ\s
                """);
        int option;
        String input = scan.nextLine();
        if (input.matches("[0-9]+")) {
            option = Integer.parseInt(input);
            boolean checkFound = false;
            switch (option) {
                case 0 -> {
                    System.out.print("Nhập id từ cần cập nhật: \n");
                    int updatedWordId; //tim tu muon update theo id
                    input = scan.nextLine();
                    if (input.matches("[0-9]+")) {
                        updatedWordId = Integer.parseInt(input);
                        for (Word word : Words) {
                            if (updatedWordId == word.getId()) {
                                System.out.println("Đã tìm được! Từ tiếng Anh mới sẽ thay vào chỗ id: " + word.getId());
                                System.out.println("Mời bạn nhập từ mới: ");
                                String newWordTarget = scan.nextLine();
                                word.setWord_target(newWordTarget);
                                System.out.println("Mời bạn nhập dạng từ của từ mới: ");
                                String newWordType = scan.nextLine();
                                word.setWord_type(newWordType);
                                System.out.println("Mời bạn nhập giải thích từ mới: ");
                                String newWordExplain = scan.nextLine();
                                word.setWord_explain(newWordExplain);
                                System.out.println("Mời bạn nhập cách đọc từ mới: ");
                                String newPronunciation = scan.nextLine();
                                word.setPronunciation(newPronunciation);
                                System.out.println("Mời bạn nhập ví dụ cho từ mới: ");
                                String newExample = scan.nextLine();
                                word.setExample(newExample);
                                checkFound = true;
                                break;
                            }
                        }
                        if (!checkFound) {
                            System.out.println("Không tìm được từ !");
                        }
                    } else {
                        System.out.println("Đầu vào không hợp lệ !");
                    }
                }
                case 1 -> {
                    System.out.print("Nhập từ cần cập nhật: \n");
                    String updatedWord = scan.nextLine(); //tim tu muon update theo ten/wordTarget
                    for (Word word : Words) {
                        if (updatedWord.equals(word.getWord_target())) {
                            System.out.println("Đã tìm được! Từ tiếng Anh mới sẽ thay chỗ của từ: " + word.getWord_target());
                            System.out.println("Mời bạn nhập dạng từ của từ mới: ");
                            String newWordType = scan.nextLine();
                            word.setWord_type(newWordType);
                            System.out.println("Mời bạn nhập phần giải thích từ mới: ");
                            String newWordExplain = scan.nextLine();
                            word.setWord_explain(newWordExplain);
                            System.out.println("Mời bạn nhập cách đọc từ mới: ");
                            String newPronunciation = scan.nextLine();
                            word.setPronunciation(newPronunciation);
                            System.out.println("Mời bạn nhập một ví dụ cách dùng từ mới: ");
                            String newExample = scan.nextLine();
                            word.setExample(newExample);
                            checkFound = true;
                            break;
                        }
                    }
                    if (!checkFound) {
                        System.out.println("Không tìm được từ !");
                    }
                }
                default -> System.out.println("Hành động không được hỗ trợ.");
            }
        } else {
            System.out.println("Đầu vào không hợp lệ !");
        }
    }

    public void removeWord() {
        boolean hasBeenRemoved = false;
        boolean choice = false;
        String input;
        System.out.print("""
                Hãy chọn cách thức xoá:
                [0] Theo id
                [1] Theo từ
                """);
        input = scan.nextLine();
        if (input.equals("1")) {
            choice = true;
        } else if (!input.equals("0")) {
            System.out.println("Đầu vào không hợp lệ, chuyển về cách thức xoá theo id.. ");
        }
        if (choice) {
            System.out.println("Hãy nhập từ cần xóa: ");
            input = scan.nextLine();
            input = input.toLowerCase();
            input = input.trim();
            for (Word word : Words) {
                if (input.equals(word.getWord_target())) {
                    Words.remove(word);
                    hasBeenRemoved = true;
                    break;
                }
            }
        } else {
            System.out.println("Hãy nhập id của từ cần xóa: ");
            int removedWordId;
            input = scan.nextLine();
            if (input.matches("[0-9]+")) {
                removedWordId = Integer.parseInt(input);
            } else {
                System.out.println("Đầu vào không hợp lệ !");
                return;
            }
            for (Word word : Words) {
                if (removedWordId == word.getId()) {
                    input = word.getWord_target();
                    Words.remove(word);
                    hasBeenRemoved = true;
                    break;
                }
            }
        }

        if (!hasBeenRemoved) {
            System.out.println("Không xóa được từ! Có vẻ như từ này không có trong danh sách.");
        } else {
            System.out.println("Bạn đã xóa từ thành công!" + "\nTừ đã xóa: " + input);
        }
    }

    public void dictionarySearcher() {
        System.out.println("Nhập từ bạn cần tìm: ");
        String prefixOfWord = scan.nextLine();
        int count = 0; // optional addition
        if (!prefixOfWord.isBlank()) {
            prefixOfWord = prefixOfWord.toLowerCase();
            prefixOfWord = prefixOfWord.trim();
        }
        System.out.printf("%-5s | %-20s\n", "ID", "Tiếng Anh");
        for (Word word : Words) {
            if (word.getWord_target().startsWith(prefixOfWord)) {
                System.out.printf("%-5s | %-20s\n", word.getId(), word.getWord_target());
                count++;
            }
        }
        System.out.println("Tìm thấy " + count + " từ bắt đầu với \"" + prefixOfWord + "\"");
    }

    public void dictionaryExportToFile() {
        try {
            String exportPath;
            System.out.println("Hãy nhập tên file: ");
            String fileName = scan.nextLine();
            if (fileName.isBlank()) {
                fileName = "dictionaries_exported.txt";
                System.out.println("Đầu vào không hợp lệ, sử dụng tên file mặc định dictionaries_exported..");
            } else {
                fileName += ".txt";
            }
            System.out.println("Hãy nhập thư mục chứa file (có thể bỏ qua và sử dụng thư mục mặc định /src/resources) ");
            exportPath = scan.nextLine();
            if (exportPath.isBlank()) {
                exportPath = "src\\resources\\";
            } else if (Files.notExists(Path.of(exportPath))) {
                System.out.println("Không tìm thấy thư mục !");
                return;
            }
            // kiem tra ki tu cuoi file la /
            if (exportPath.charAt(exportPath.length() - 1) != '\\') {
                exportPath = exportPath + "\\";
            }

            File exportedFile = new File(exportPath + fileName);
            if (exportedFile.exists()) {
                System.out.println("File " + fileName + " đã tồn tại. Để ghi đè, nhấn 'y' ");
                String confirmation = scan.nextLine();
                if (confirmation.equalsIgnoreCase("y")) {
                    boolean wasDeleted = exportedFile.delete();
                    if (!wasDeleted) {
                        System.out.println("Không thể xoá file cũ...");
                    }
                } else {
                    System.out.println("Đang trở về menu chính... ");
                    return;
                }
            }

            System.out.println("Đang xuất ra file.. ");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(exportPath + fileName));
            String line;
            int j = 0;
            while (j < Words.size()) {
                line = String.format("%-5d %-20s %-15s %-40s %-20s %s%n", j + 1, Words.get(j).getWord_target(), Words.get(j).getWord_type(), Words.get(j).getWord_explain(), Words.get(j).getPronunciation(), Words.get(j).getExample());
                bufferedWriter.write(line);
                j++;
            }
            bufferedWriter.close();
            System.out.println("Đã xuất ra file ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}