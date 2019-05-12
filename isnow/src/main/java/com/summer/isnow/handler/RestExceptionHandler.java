package com.summer.isnow.handler;

import com.summer.icommon.exception.GeneralException;
import com.summer.icommon.exception.NoAuthorizationException;
import com.summer.icommon.exception.RequestParameterErrorException;
import com.summer.icommon.exception.ServerErrorException;
import com.summer.icommon.utils.ResponseCode;
import com.summer.icommon.utils.ResponseModel;
import com.summer.isnow.dto.ArgumentInvalidResult;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;


/**
 * 异常处理器。该类会处理所有在执行标有@RequestMapping注解的方法时发生的异常
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private Logger logger = LoggerFactory.getLogger("RestExceptionHandler");

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseModel<ResponseCode> baseErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		logger.error("---Global Exception Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
		return new ResponseModel<ResponseCode>(ResponseCode.SERVER_ERROR);
	}

	/* @ExceptionHandler(value = BaseException.class)
	@ResponseBody
	public Object baseErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		logger.error("---BaseException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
		return e.getMessage();
	}

   @ExceptionHandler(value = MyException1.class)
    @ResponseBody
    public Object handleMyException1(HttpServletRequest req, Exception e) throws Exception {
        logger.error("---MyException1 Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        return e.getMessage();
    }*/

    /*@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("---DefaultException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        return e.getMessage();
    }*/

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(value = GeneralException.class)
	@ResponseBody
	public ResponseModel handleGeneralException(HttpServletRequest req, GeneralException ex) {
		logger.error("handleGeneralException", ex);
		return new ResponseModel(ex.getResponseCode());
	}

	/**
	 * 处理@RequestParam错误, 即参数不足
	 *
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("handleMissingServletRequestParameter", ex);
		//return new ResponseEntity<Object>(new ResponseModel(ResponseCode.REQUEST_ERROR), status);
		return handleExceptionInternal(new RequestParameterErrorException(ex),null, headers, BAD_REQUEST, request);
	}


	protected ResponseEntity<Object> handleExceptionInternal(GeneralException ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("handleExceptionInternal", ex);
		ResponseModel<Object> model = new ResponseModel<Object>(ex.getResponseCode(), body);
		return new ResponseEntity<Object>(model, headers, status);
	}

	// API

	// 400

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return handleExceptionInternal(new RequestParameterErrorException(ex), null, headers, BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
		return handleExceptionInternal(new RequestParameterErrorException(ex), null, headers, BAD_REQUEST, request);
	}

	@ExceptionHandler({ConstraintViolationException.class})
	protected ResponseEntity<Object> handleBeanValidation(final ConstraintViolationException ex, final WebRequest request) {
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		List<ArgumentInvalidResult> invalidArguments = getConstraintViolationErrors(violations);
		ResponseModel<List<ArgumentInvalidResult>> toReturn = new ResponseModel<List<ArgumentInvalidResult>>(ResponseCode.REQUEST_PARAMETER_ERROR, invalidArguments);
		return new ResponseEntity<Object>(toReturn, BAD_REQUEST);
	}

	@ExceptionHandler({TransactionSystemException.class})
	protected ResponseEntity<Object> handleTransactionValidation(final RuntimeException ex, final WebRequest request) {
		return handleExceptionInternal(new RequestParameterErrorException(ex), null, new HttpHeaders(), BAD_REQUEST, request);
	}

	@ExceptionHandler({InvalidDataAccessApiUsageException.class, DataAccessException.class, IllegalArgumentException.class})
	protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
		return handleExceptionInternal(new RequestParameterErrorException(ex), null, new HttpHeaders(), BAD_REQUEST, request);
	}

	@ExceptionHandler({AuthenticationException.class})
	protected ResponseEntity<Object> handleBadCredentials(final RuntimeException ex, final WebRequest request) {
		return handleExceptionInternal(new NoAuthorizationException(ex), null, new HttpHeaders(), FORBIDDEN, request);
	}

	// 500

	@ExceptionHandler({NullPointerException.class, IllegalStateException.class})
	public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
		return handleExceptionInternal(new ServerErrorException(ex), null, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("handleMethodArgumentNotValid", ex);
		List<ArgumentInvalidResult> invalidArguments = getBindErrors(ex.getBindingResult());

		ResponseModel<List<ArgumentInvalidResult>> toReturn = new ResponseModel<List<ArgumentInvalidResult>>(ResponseCode.REQUEST_PARAMETER_ERROR, invalidArguments);
		return new ResponseEntity<Object>(toReturn, BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
		logger.error("handleBindException", ex);
		List<ArgumentInvalidResult> invalidArguments = getBindErrors(ex.getBindingResult());

		ResponseModel<List<ArgumentInvalidResult>> toReturn = new ResponseModel<List<ArgumentInvalidResult>>(ResponseCode.REQUEST_PARAMETER_ERROR, invalidArguments);
		return new ResponseEntity<Object>(toReturn, BAD_REQUEST);
	}

	private List<ArgumentInvalidResult> getBindErrors(BindingResult bindingResult) {
		// 按需重新封装需要返回的错误信息
		List<ArgumentInvalidResult> invalidArguments = new ArrayList<ArgumentInvalidResult>();
		// 解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
		for (FieldError error : bindingResult.getFieldErrors()) {
			ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
			invalidArgument.setDefaultMessage(error.getDefaultMessage());
			invalidArgument.setField(error.getField());
			invalidArgument.setRejectedValue(error.getRejectedValue());
			invalidArguments.add(invalidArgument);
		}
		return invalidArguments;
	}

	private List<ArgumentInvalidResult> getConstraintViolationErrors(Set<ConstraintViolation<?>> violations) {
		// 按需重新封装需要返回的错误信息
		List<ArgumentInvalidResult> invalidArguments = new ArrayList<ArgumentInvalidResult>();
		// 解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
		for (ConstraintViolation<?> violation : violations ) {
			ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
			invalidArgument.setDefaultMessage(violation.getMessage());
			invalidArgument.setField(violation.getPropertyPath().toString());
			invalidArgument.setRejectedValue(violation.getInvalidValue());
			invalidArguments.add(invalidArgument);
		}
		return invalidArguments;
	}

}