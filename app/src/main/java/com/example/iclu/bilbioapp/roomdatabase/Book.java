package com.example.iclu.bilbioapp.roomdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


@Entity
public class Book implements Parcelable, Comparable<Book> {
    public static String ARG_BOOK = "ARG_BOOK";

    //region declaration
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "book_title")
    private String bookTitle;

    @ColumnInfo(name = "book_author")
    private String bookAuthor;

    @ColumnInfo(name = "book_thumbnail")
    private String bookThumbnail;

    @ColumnInfo(name = "book_page_count")
    private int bookPageCount;

    @ColumnInfo(name = "book_publisher")
    private String bookPublisher;

    @ColumnInfo(name = "book_year")
    private String bookDate;

    @ColumnInfo(name = "book_isbn")
    private String bookISBN;

    @ColumnInfo(name = "book_categories")
    private String bookCategories;

    @ColumnInfo(name = "book_description")
    private String bookDescription;
    //endregion

    //region Book constructors
    public Book() {
    }

    public Book(String bookTitle, String bookAuthor, String bookThumbnail, int bookPageCount, String bookPublisher, String bookDate, String bookISBN, String bookCategories, String bookDescription) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookThumbnail = bookThumbnail;
        this.bookPageCount = bookPageCount;
        this.bookPublisher = bookPublisher;
        this.bookDate = bookDate;
        this.bookISBN = bookISBN;
        this.bookCategories = bookCategories;
        this.bookDescription = bookDescription;
    }
//endregion

    //region parcelable
    public Book(Parcel parcel) {
        id = parcel.readInt();
        bookTitle = parcel.readString();
        bookAuthor = parcel.readString();
        bookThumbnail = parcel.readString();
        bookPageCount = parcel.readInt();
        bookPublisher = parcel.readString();
        bookDate = parcel.readString();
        bookISBN = parcel.readString();
        bookCategories = parcel.readString();
        bookDescription = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(bookTitle);
        parcel.writeString(bookAuthor);
        parcel.writeString(bookThumbnail);
        parcel.writeInt(bookPageCount);
        parcel.writeString(bookPublisher);
        parcel.writeString(bookDate);
        parcel.writeString(bookISBN);
        parcel.writeString(bookCategories);
        parcel.writeString(bookDescription);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        @Override
        public Book[] newArray(int i) {
            return new Book[0];
        }
    };

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookThumbnail='" + bookThumbnail + '\'' +
                ", bookPageCount=" + bookPageCount +
                ", bookPublisher='" + bookPublisher + '\'' +
                ", bookDate='" + bookDate + '\'' +
                ", bookISBN='" + bookISBN + '\'' +
                ", bookCategories='" + bookCategories + '\'' +
                ", bookDescription='" + bookDescription + '\'' +
                '}';
    }
    //endregion

    /**
     * The ColumnInfo aren't necessary
     * They allow naming of column so it is different than the name given (ex : String bookTitle)
     * If not used, the database will use the name given
     * Refer to normal database to see how to write it without columns
     */

    //region getters and setters
    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookThumbnail() {
        return bookThumbnail;
    }

    public void setBookThumbnail(String bookThumbnail) {
        this.bookThumbnail = bookThumbnail;
    }

    public int getBookPageCount() {
        return bookPageCount;
    }

    public void setBookPageCount(int bookPageCount) {
        this.bookPageCount = bookPageCount;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getBookCategories() {
        return bookCategories;
    }

    public void setBookCategories(String bookCategories) {
        this.bookCategories = bookCategories;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookDescription() {
        return bookDescription;
    }
    //endregion


    @Override
    public int compareTo(@NonNull Book book) {
        return bookTitle.compareToIgnoreCase(book.getBookTitle());
    }
}