package com.diogoamsilva.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.diogoamsilva.exception.UnsuportedMathOperationException;
import com.diogoamsilva.utils.MathUtils;
import com.diogoamsilva.utils.SimpleMath;
import com.diogoamsilva.validations.Validator;

@RestController
public class MathController {
	
	SimpleMath math = new SimpleMath();
		
	@RequestMapping(value="/sum/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
		Validator.checkIfIsNumeric(numberOne, numberTwo);
		return math.sum(MathUtils.convertToDouble(numberOne), MathUtils.convertToDouble(numberTwo));		
	}
	
	@RequestMapping(value="/subtration/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double subtration(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
		Validator.checkIfIsNumeric(numberOne, numberTwo);
		return math.subtraction(MathUtils.convertToDouble(numberOne), MathUtils.convertToDouble(numberTwo));		
	
	}
	
	@RequestMapping(value="/multiplication/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double multiplication(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)  {
		Validator.checkIfIsNumeric(numberOne, numberTwo);
		return math.multiplication(MathUtils.convertToDouble(numberOne), MathUtils.convertToDouble(numberTwo));		
	
	}
	
	@RequestMapping(value="/division/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double division(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)  {
		Validator.checkIfIsNumeric(numberOne, numberTwo);
		return math.division(MathUtils.convertToDouble(numberOne), MathUtils.convertToDouble(numberTwo));		

	}
	
	@RequestMapping(value="/average/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double average(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)  {
		Validator.checkIfIsNumeric(numberOne, numberTwo);
		return math.average(MathUtils.convertToDouble(numberOne), MathUtils.convertToDouble(numberTwo));		

	}
	
	@RequestMapping(value="/squareRoot/{number}", method=RequestMethod.GET)
	public Double squareRoot(@PathVariable("number") String number)  {
		Validator.checkIfIsNumeric(number);
		return math.squareRoot(MathUtils.convertToDouble(number));		
	
	}
	
}
