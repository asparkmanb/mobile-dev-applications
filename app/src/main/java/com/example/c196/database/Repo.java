package com.example.c196.database;

import android.app.Application;

import com.example.c196.dao.AssesssmentsDAO;
import com.example.c196.dao.ClassesDAO;
import com.example.c196.dao.TermDAO;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Classes;
import com.example.c196.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repo {
    private TermDAO mTermDAO;
    private ClassesDAO mClassesDAO;
    private AssesssmentsDAO mAssessmentsDAO;
    private List<Term> mAllTerms;
    private List<Classes> mAllClasses;
    private List<Assessments> mAllAssessments;

    private List<Classes> mGetAllAssociatedClasses;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repo(Application application) {
        StudentTrackerDatabase db = StudentTrackerDatabase.getDatabase(application);
        mTermDAO = db.termDAO();
        mClassesDAO = db.classesDAO();
        mAssessmentsDAO = db.assesssmentsDAO();
    }

    public List<Classes> getAllAssociatedClasses(int termID){
        databaseExecutor.execute(() -> {
            mGetAllAssociatedClasses = mClassesDAO.getAllAssociatadClasses(termID);
        });
        return mGetAllAssociatedClasses;
    }
    public List<Term> getAllTerms() {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllTerms;
    }

    public List<Classes> getAllClasses() {
        databaseExecutor.execute(() -> {
            mAllClasses = mClassesDAO.getAllClasses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllClasses;
    }

    public List<Assessments> getAllAssessments() {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentsDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllAssessments;
    }

    public void insert(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Classes classes) {
        databaseExecutor.execute(() -> {
            mClassesDAO.insert(classes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Assessments assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentsDAO.insert(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Classes classes) {
        databaseExecutor.execute(() -> {
            mClassesDAO.update(classes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Assessments assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentsDAO.update(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Classes classes) {
        databaseExecutor.execute(() -> {
            mClassesDAO.delete(classes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Assessments assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentsDAO.delete(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
