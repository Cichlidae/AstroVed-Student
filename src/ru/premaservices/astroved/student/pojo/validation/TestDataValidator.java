package ru.premaservices.astroved.student.pojo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.premaservices.astroved.student.pojo.TestData;

public class TestDataValidator implements ConstraintValidator<TestDataConstraint, TestData> {

	@Override
	public void initialize(TestDataConstraint arg0) {
	}

	@Override
	public boolean isValid(TestData tests, ConstraintValidatorContext arg1) {
		
		if (tests.getExam_1() && !tests.getCourse_1_test()) return false;
		if (tests.getExam_2() && !tests.getCourse_2_test()) return false;
		if (tests.getExam_3() && !tests.getCourse_3_test()) return false;
		
		if (tests.getExam_2() && !tests.getExam_1()) return false;
		if (tests.getExam_3() && !tests.getExam_2()) return false;
		if (tests.getDiploma() && !tests.getExam_3()) return false;
		
		if (tests.getExam_1() && tests.getDiploma_1() == null) return false;
		if (tests.getExam_2() && tests.getDiploma_2() == null) return false;
		if (tests.getExam_3() && tests.getDiploma_3() == null) return false;		
		if (tests.getDiploma() && tests.getFinalDiploma() == null) return false;
		
		if (tests.getExam_1() && (tests.getDiploma_1().getNumber() == null || tests.getDiploma_1().getDiplomaDate() == null)) {
			return false;
		}
		if (tests.getExam_2() && (tests.getDiploma_2().getNumber() == null || tests.getDiploma_2().getDiplomaDate() == null)) {
			return false;
		}
		if (tests.getExam_3() && (tests.getDiploma_3().getNumber() == null || tests.getDiploma_3().getDiplomaDate() == null)) {
			return false;
		}
		if (tests.getDiploma() && (tests.getFinalDiploma().getNumber() == null || tests.getFinalDiploma().getDiplomaDate() == null)) {
			return false;
		}
		return true;
	}

}
