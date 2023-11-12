package common;

import java.io.*;

public class Book implements Externalizable {
    private String author;
    private String title;
    private int publicationYear;
    private int pagesNum;
    private int publicationNum;
    private boolean hasImages;
    private boolean hasSolidCover;
    private int circulation;

    public Book() {

    }

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
}