package dataModel;

import java.io.Serializable;

import dataModel.Category;

/**
 * An object of the <code>Book</code> class contains book information
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	private String isbn;
	private String title;
	private String authors;
	private String publishingHouse;
	private int publicationYear;
	private int reprintYear;
	private Category category;
	private Language language;
	private String bookcase;
	private int bookings;

	/**
	 * Create a new object <code>Book</code> with the information in input
	 * 
	 * @param isbn
	 *            is the book ISBN
	 * @param title
	 *            is the book title
	 * @param authors
	 *            are the book authors
	 * @param publishingHouse
	 *            is the book publishing house
	 * @param publicationYear
	 *            is the book publication year
	 * @param reprintYear
	 *            is the book reprint year
	 * @param category
	 *            is the book category
	 * @param language
	 *            is the book language
	 * @param bookcase
	 *            is the book bookcase
	 * @param bookings
	 *            is the book bookings number
	 */
	public Book(String isbn, String title, String authors, String publishingHouse, int publicationYear, int reprintYear,
			Category category, Language language, String bookcase, int bookings) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.authors = authors;
		this.publishingHouse = publishingHouse;
		this.publicationYear = publicationYear;
		this.reprintYear = reprintYear;
		this.category = category;
		this.language = language;
		this.bookcase = bookcase;
		this.bookings = bookings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Book) {
			Book book = (Book) obj;
			return this.getIsbn().equals(book.getIsbn());
		}
		return false;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the authors
	 */
	public String getAuthors() {
		return authors;
	}

	/**
	 * @param authors
	 *            the authors to set
	 */
	public void setAuthors(String authors) {
		this.authors = authors;
	}

	/**
	 * @return the publishingHouse
	 */
	public String getPublishingHouse() {
		return publishingHouse;
	}

	/**
	 * @param publishingHouse
	 *            the publishingHouse to set
	 */
	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}

	/**
	 * @return the publicationYear
	 */
	public int getPublicationYear() {
		return publicationYear;
	}

	/**
	 * @param publicationYear
	 *            the publicationYear to set
	 */
	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	/**
	 * @return the reprintYear
	 */
	public int getReprintYear() {
		return reprintYear;
	}

	/**
	 * @param reprintYear
	 *            the reprintYear to set
	 */
	public void setReprintYear(int reprintYear) {
		this.reprintYear = reprintYear;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the language
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 * @return the bookcase
	 */
	public String getBookcase() {
		return bookcase;
	}

	/**
	 * @param bookcase
	 *            the bookcase to set
	 */
	public void setBookcase(String bookcase) {
		this.bookcase = bookcase;
	}

	/**
	 * @return the bookings
	 */
	public int getBookings() {
		return bookings;
	}

	/**
	 * @param bookings
	 *            the bookings to set
	 */
	public void setBookings(int bookings) {
		this.bookings = bookings;
	}

}
