package automatedgrader.observer;

import java.util.List;

import automatedgrader.strategy.EvaluationResult;

// Observer interface
public interface PDFObserver {
   void updatePDF(Submission submission, List<EvaluationResult> testResults);
} 