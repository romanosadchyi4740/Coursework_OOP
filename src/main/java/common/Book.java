package common;

// імпорт усіх класів, пов'язаних із вводом та виводом
import java.io.*;

// extends - наслідування від класу; implements - наслідування від інтерфейсу
public class Book implements Externalizable {
    private String author;
    private String title;
    private int publicationYear;
    private int pagesNum;
    private int publicationNum;
    private boolean hasImages;
    private boolean hasSolidCover;
    private int circulation;

    // Конструктор за замовчуванням
    public Book() {

    }

    // Конструктор, який ініціалізує усі поля класу
    public Book(String author,
                String title,
                int publicationYear,
                int pagesNum,
                int publicationNum,
                boolean hasImages,
                boolean hasSolidCover,
                int circulation) {
        this.author = author;
        this.title = title;
        this.publicationYear = publicationYear;
        this.pagesNum = pagesNum;
        this.publicationNum = publicationNum;
        this.hasImages = hasImages;
        this.hasSolidCover = hasSolidCover;
        this.circulation = circulation;
    }

    // Якщо змінна isMove = true, то працює в якості конструктора переміщень, інакше, як конструктор копій
    public Book(Book original, boolean isMove) {
        this.author = original.author;
        this.title = original.title;
        this.publicationYear = original.publicationYear;
        this.pagesNum = original.pagesNum;
        this.publicationNum = original.publicationNum;
        this.hasImages = original.hasImages;
        this.hasSolidCover = original.hasSolidCover;
        this.circulation = original.circulation;

        if (isMove) {
            original = null;
        }
    }

    // Гетери (селектори) та сетери (модифікатори) для
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getPagesNum() {
        return pagesNum;
    }

    public void setPagesNum(int pagesNum) {
        this.pagesNum = pagesNum;
    }

    public int getPublicationNum() {
        return publicationNum;
    }

    public void setPublicationNum(int publicationNum) {
        this.publicationNum = publicationNum;
    }

    public boolean isHasImages() {
        return hasImages;
    }

    public void setHasImages(boolean hasImages) {
        this.hasImages = hasImages;
    }

    public boolean isHasSolidCover() {
        return hasSolidCover;
    }

    public void setHasSolidCover(boolean hasSolidCover) {
        this.hasSolidCover = hasSolidCover;
    }

    public int getCirculation() {
        return circulation;
    }

    public void setCirculation(int circulation) {
        this.circulation = circulation;
    }

    // У Java для того, щоб зробити об'єкт класу доступним для серіалізації необхідно унаслідувати цей клас від
    // інтерфейсу Serializable (тоді не потрібно перевизначати жодних методів, оскільки інтерфейс працює лише як певна
    // мітка, що цей клас можна серіалізувати). В даному випадку Java надасть механізм серіалізації за замовчуванням.
    // У даному випадку я унаслідував клас від інтерфейсу Externalizable, який надає можливість самостійно описати
    // механізм серіалізації, щоб виконати завдання, пов'язане з перевизначенням операторів вводу та виводу.

    // Перевизначення методу інтерфейсу Externalizable для запису об'єкта у файл
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(author);
        out.writeObject(title);
        out.writeInt(publicationYear);
        out.writeInt(pagesNum);
        out.writeInt(publicationNum);
        out.writeBoolean(hasImages);
        out.writeBoolean(hasSolidCover);
        out.writeInt(circulation);
    }

    // Перевизначення методу інтерфейсу Externalizable для зчитування об'єкта з файлу
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        author = (String) in.readObject();
        title = (String) in.readObject();
        publicationYear = in.readInt();
        pagesNum = in.readInt();
        publicationNum = in.readInt();
        hasImages = in.readBoolean();
        hasSolidCover = in.readBoolean();
        circulation = in.readInt();
    }

    // Перевизначення методу toString()
    @Override
    public String toString() {
        return "Author: " + author + '\n' +
                "Title: " + title + '\n' +
                "Year of publication: " + publicationYear + '\n' +
                "Number of pages" + pagesNum + '\n' +
                "Publication number: " + publicationNum + '\n' +
                "Is illustrated: " + hasImages + '\n' +
                "Has solid cover: " + hasSolidCover + '\n' +
                "Circulation: " + circulation;
    }
}