package automatedgrader.observer;

// Subject Interface
public interface SubmissionSubject {
  void attachObserver(PDFObserver observer);
  void detachObserver(PDFObserver observer);
  void notifyObserver();
} 

