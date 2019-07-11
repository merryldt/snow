package com.summer.isnow.handler;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.summer.icommon.exception.GeneralException;
import com.summer.icommon.utils.ResponseCode;
import com.summer.icommon.utils.ResponseModel;
import com.summer.isnow.dto.ArgumentInvalidResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static org.springframework.http.HttpStatus.*;


/**
 * 异常处理器。该类会处理所有在执行标有@RequestMapping注解的方法时发生的异常
 * @RestControllerAdvice =  @ControllerAdvice +	@ResponseBody
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(value = GeneralException.class)
	@ResponseBody
	public ResponseModel defaultErrorHandler(HttpServletRequest req, Exception e) {
		String errorPosition = "";
		//如果错误堆栈信息存在
		if (e.getStackTrace().length > 0) {
			StackTraceElement element = e.getStackTrace()[0];
			String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName();
			int lineNumber = element.getLineNumber();
			errorPosition = fileName + ":" + lineNumber;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", ((GeneralException)e).getResponseCode().getCode());
		jsonObject.put("msg", ((GeneralException)e).getResponseCode().getMessage());
		JSONObject errorObject = new JSONObject();
		errorObject.put("errorLocation", e.toString() + "    错误位置:" + errorPosition);
		jsonObject.put("info", errorObject);
		logger.error("异常", e);
		return new ResponseModel(((GeneralException)e).getResponseCode(),jsonObject);
	}
		/**
		 * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
		 * @param model
		 */
		@ModelAttribute
		public void addAttributes(Model model) {
			model.addAttribute("author", "坎布里奇");
		}

	/**
	 * 没有权限访问
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	public ResponseModel unauthorizedExceptionHandler(UnauthorizedException ex) {
		logger.error("unauthorizedExceptionHandler" +ex);
		return new ResponseModel(ResponseCode.PERMISSION_DENIED);
	}

	/**
	 * 授权失败
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({AuthenticationException.class})
	protected ResponseEntity<Object> handleBadCredentials(final RuntimeException ex, final WebRequest request) {
		ResponseModel responseModel= new ResponseModel(ResponseCode.REQUEST_METHOD_ERROR);
		logger.error("handleBadCredentials", ex);
		return handleExceptionInternal(new GeneralException(ex), null, new HttpHeaders(), FORBIDDEN, request);
	}

	/**
	 * 未登录报错拦截，没有token
	 * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
	 */
	@ExceptionHandler(UnauthenticatedException.class)
	@ResponseBody
	public ResponseModel<Object> unauthenticatedException(UnauthenticatedException ex) {
		logger.error("unauthenticatedException", ex);
		return new ResponseModel<>(ResponseCode.LOGIN_OUT_OF_DATE);
	}


	/**
	 * 请求参数异常
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.error("handleHttpMessageNotReadable Bad Request case :{},  error :{} ",ex.getCause(),ex);
		ResponseModel responseModel= new ResponseModel(ResponseCode.UNSUPPORTED_MEDIA_TYPE);
		return new ResponseEntity<Object>(responseModel, BAD_REQUEST);
	}

	/**
	 * 参数类型不匹配
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
		logger.error("request param TypeMismatch error:{} , request :{}",  ex.getPropertyName(),ex.getRequiredType(),ex);
		ResponseModel toReturn = new ResponseModel<>(ResponseCode.PARAMETER_TYPE_MISMATCH);
		return new ResponseEntity<Object>(toReturn, BAD_REQUEST);
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
	/**
	 * 请求参数异常
	 * @param ex
	 * @param request
	 * @return
	 */

	@ExceptionHandler({TransactionSystemException.class})
	protected ResponseEntity<Object> handleTransactionValidation(final RuntimeException ex, final WebRequest request) {
		logger.error("handleTransactionValidation", ex);
		return handleExceptionInternal(new GeneralException(ex), null, new HttpHeaders(), BAD_REQUEST, request);
	}

	@ExceptionHandler({ConstraintViolationException.class})
	protected ResponseEntity<Object> handleBeanValidation(final ConstraintViolationException ex, final WebRequest request) {
		logger.error("handleBeanValidation", ex);
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		List<ArgumentInvalidResult> invalidArguments = getConstraintViolationErrors(violations);
		ResponseModel<List<ArgumentInvalidResult>> toReturn = new ResponseModel<List<ArgumentInvalidResult>>(ResponseCode.REQUEST_PARAMETER_ERROR, invalidArguments);
		return new ResponseEntity<Object>(toReturn, BAD_REQUEST);
	}

	/**
	 * 数据库操作出现异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value={SQLException.class,DataAccessException.class,InvalidDataAccessApiUsageException.class})
	@ResponseBody
	protected ResponseEntity<Object> systemError(Exception e){
		logger.error("occurs error when execute method ,message {}",e.getMessage());
		logger.error("occurs error when execute method ,message {} ",e);
		ResponseModel responseModel= new ResponseModel(ResponseCode.DATABASE_ERROR);
		return new ResponseEntity<Object>(responseModel, INTERNAL_SERVER_ERROR);
	}



	/**
	 * 500
	 * 服务器异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({IllegalStateException.class})
	public ResponseEntity<Object> handleInternal(final RuntimeException ex) {
		logger.error("RestExceptionHandler handleInternal error ：{} ", ex.getCause(),ex);
		ResponseModel responseModel= new ResponseModel(ResponseCode.SERVER_ERROR);
		return new ResponseEntity<Object>(responseModel, INTERNAL_SERVER_ERROR);
	}

	/**
	 * 500错误
	 * @param ex
	 * @return
	 */
//	@ExceptionHandler({ConversionNotSupportedException.class,HttpMessageNotWritableException.class})
//	@ResponseBody
//	public ResponseEntity<Object> server500(RuntimeException ex){
//		logger.error("RestExceptionHandler server500 error ：{} ", ex.getCause(),ex);
//		ResponseModel responseModel= new ResponseModel(ResponseCode.SERVER_ERROR);
//		return new ResponseEntity<Object>(responseModel, INTERNAL_SERVER_ERROR);
//	}

	/**
	 * 请求参数的方式
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			headers.setAccept(mediaTypes);
		}
		logger.error("Request media type not support :{}, please use :{} ",ex.getContentType(),ex.getSupportedMediaTypes());
		ResponseModel responseModel= new ResponseModel(ResponseCode.UNSUPPORTED_MEDIA_TYPE);
		return new ResponseEntity<Object>(responseModel, UNSUPPORTED_MEDIA_TYPE);
	}
	/**
	 * 处理@RequestParam错误, 即参数不足
	 *
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("parameter is missing  parameterName :{} ",ex.getParameterName());
		ResponseModel responseModel= new ResponseModel(ResponseCode.BAD_REQUEST);
		return new ResponseEntity<Object>(responseModel, BAD_REQUEST);
	}
	/**
	 * GET/POST请求方法错误的拦截器
	 * 因为开发时可能比较常见,而且发生在进入controller之前,上面的拦截器拦截不到这个错误
	 * 所以定义了这个拦截器
	 */
	@ResponseBody
	@Override
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
		if (!CollectionUtils.isEmpty(supportedMethods)) {
			headers.setAllow(supportedMethods);
		}
		logger.error("Request method not support :{}, please use :{} ",ex.getMethod(),ex.getSupportedMethods());
		ResponseModel responseModel= new ResponseModel(ResponseCode.REQUEST_METHOD_ERROR);
		return new ResponseEntity<Object>(responseModel, METHOD_NOT_ALLOWED);
	}

	/**
	 * //406错误 表示客户端错误，表示请求的资源的内容特性无法满足请求头中的条件，因而无法生成响应实体
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Override
	public ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,HttpHeaders headers, HttpStatus status, WebRequest request){
		logger.error("Request header accept is faile   ",ex);
		ResponseModel responseModel= new ResponseModel(ResponseCode.NOT_ACCEPTABLE);
		return new ResponseEntity<Object>(responseModel, NOT_ACCEPTABLE);
	}

	/**
	 * 运行时异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(GeneralException.class)
	@ResponseBody
	public ResponseEntity<Object> runtimeExceptionHandler(RuntimeException  ex) {
		logger.error("RuntimeException : {} ",ex);
		ResponseModel responseModel= new ResponseModel(ResponseCode.SERVER_RUNTIME_ERROR);
		return new ResponseEntity<Object>(responseModel, INTERNAL_SERVER_ERROR);
	}

	/**
	 * 空指针异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	public ResponseEntity<Object> nullPointerExceptionHandler(NullPointerException ex) {
		ex.printStackTrace();
		logger.error("NullPointerException : {} ",ex);
		ResponseModel responseModel= new ResponseModel(ResponseCode.NULL_POINTER_ERROR);
		return new ResponseEntity<Object>(responseModel, INTERNAL_SERVER_ERROR);
	}

	/**
	 * 类型转换异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ClassCastException.class)
	@ResponseBody
	public ResponseEntity<Object> classCastExceptionHandler(ClassCastException ex) {
		ex.printStackTrace();
		logger.error("ClassCastException : {} ",ex);
		ResponseModel responseModel= new ResponseModel(ResponseCode.CLASS_CAST_ERROR);
		return new ResponseEntity<Object>(responseModel, INTERNAL_SERVER_ERROR);
	}

	/**
	 * IO异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(IOException.class)
	@ResponseBody
	public ResponseEntity<Object> iOExceptionHandler(IOException ex) {
		ex.printStackTrace();
		logger.error("IOException : {} ",ex);
		ResponseModel responseModel= new ResponseModel(ResponseCode.IO_ERROR);
		return new ResponseEntity<>(responseModel, INTERNAL_SERVER_ERROR);
	}

	/**
	 * 未知方法异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(NoSuchMethodException.class)
	@ResponseBody
	public ResponseEntity<Object> noSuchMethodExceptionHandler(NoSuchMethodException ex) {
		ex.printStackTrace();

		logger.error("NoSuchMethodException : {} ",ex);
		ResponseModel responseModel= new ResponseModel(ResponseCode.NO_SUCH_METHOD_ERROR);
		return new ResponseEntity<>(responseModel, INTERNAL_SERVER_ERROR);
	}

	/**
	 * 数组越界异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(IndexOutOfBoundsException.class)
	@ResponseBody
	public ResponseEntity<Object> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
		ex.printStackTrace();
		logger.error("IndexOutOfBoundsException : {} ",ex);
		ResponseModel responseModel= new ResponseModel(ResponseCode.INDEX_OUTOF_BOUNDS_ERROR);
		return new ResponseEntity<>(responseModel, INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(TokenExpiredException.class)
	public Object TokenExpiredException(TokenExpiredException exception) {
		System.out.println();
		System.out.println(exception.getMessage());
		System.out.println();

		Map<String, String> map = new HashMap<>(16);
		map.put(TokenExpiredException.class.getSimpleName(), exception.getMessage());
		return map;
	}

	@ExceptionHandler(JWTVerificationException.class)
	public Object JWTVerificationException(JWTVerificationException exception) {
		System.out.println();
		System.out.println(exception.getMessage());
		System.out.println();

		Map<String, String> map = new HashMap<>(16);
		map.put(JWTVerificationException.class.getSimpleName(), exception.getMessage());
		return map;
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