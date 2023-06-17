package Library;

public class EBook extends Book {

    private int downloadSizeMB;

    // Using the superclass constructor and adding the download size
    public EBook(String title, String author, int pages, int downloadSizeMB) {
        super(title, author, pages, 1);
        validateDownloadSize(downloadSizeMB);
        this.downloadSizeMB = downloadSizeMB;
    }

    // Getters and setters for the variable which is not declared in the superclass.
    public int getDownloadSizeMB() {
        return downloadSizeMB;
    }

    public void setDownloadSizeMB(int downloadSizeMB) {
        validateDownloadSize(downloadSizeMB);
        this.downloadSizeMB = downloadSizeMB;
    }

    // Overriding number of copies to be 1, as ebooks are digital.
    @Override
    public int getCopies() {
        return 1;
    }

    @Override
    public void setCopies(int copies) {
        this.copies = 1;
    }

    // Private helping methods for the constructor.
    private void validateDownloadSize(int downloadSizeMB) {
        ValidateMethods.checkDownloadSize(downloadSizeMB);
    }


    @Override
    public String toString() {
        return String.format("|EBook: %5s |Author: %5s |Pages: %2s |Size: %2s%n", this.getTitle(), this.getAuthor(), this.getPages(), this.getDownloadSizeMB());
    }
}