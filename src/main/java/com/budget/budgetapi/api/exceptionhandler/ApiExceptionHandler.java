package com.budget.budgetapi.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.MappingException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.budget.budgetapi.domain.exception.DomainException;
import com.budget.budgetapi.domain.exception.EntityInUseException;
import com.budget.budgetapi.domain.exception.EntityNotfoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String ERROR_MSG_GENERIC = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(EntityNotfoundException.class)
	public ResponseEntity<?> handleEntityNotFound(EntityNotfoundException ex, WebRequest req) {
		ProblemApiType problemApiType = ProblemApiType.RESOURCE_NOT_FOUND;
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		ProblemApi problemApi = instanceProblemApi(httpStatus, problemApiType, ex.getMessage())
				.userMessage(ex.getMessage())
				.build();

		return handleExceptionInternal(ex, problemApi, new HttpHeaders(), httpStatus, req);
	}

	@ExceptionHandler(DomainException.class)
	public ResponseEntity<?> handleDomain(DomainException ex, WebRequest req) {
		ProblemApiType problemApiType = ProblemApiType.DOMAIN;
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ProblemApi problemApi = instanceProblemApi(httpStatus, problemApiType, ex.getMessage())
				.userMessage(ex.getMessage())
				.build();

		return handleExceptionInternal(ex, problemApi, new HttpHeaders(), httpStatus, req);
	}

	@ExceptionHandler(MappingException.class)
	public ResponseEntity<Object> handleMappingException(MappingException ex, WebRequest req) {
		ProblemApiType problemApiType = ProblemApiType.INVALID_DATA;
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail = "Erro de conversão de data. Data deve estar no formato [yyyy-MM-dd].";
		ProblemApi problemApi = instanceProblemApi(status, problemApiType, detail)
				.userMessage(
						detail)
				.build();

		return handleExceptionInternal(ex, problemApi, new HttpHeaders(), status, req);
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<Object> handleInvalidParameter(InvalidDataAccessApiUsageException ex, WebRequest req) {
		ProblemApiType problemApiType = ProblemApiType.INVALID_PARAMETER;
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail = "Parâmetro(s) inválidos. Insira os valores no formato correto e tente novamente.";
		ProblemApi problemApi = instanceProblemApi(status, problemApiType, ex.getMessage())
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problemApi, new HttpHeaders(), status, req);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest req) {
		ProblemApiType problemApiType = ProblemApiType.FORBIDDEN;
		HttpStatus status = HttpStatus.FORBIDDEN;
		String detail = "Você não tem permissão para realizar determinada ação.";
		ProblemApi problemApi = instanceProblemApi(status, problemApiType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problemApi, new HttpHeaders(), status, req);
	}

	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<Object> handleEntityInUse(EntityInUseException ex, WebRequest req) {
		ProblemApiType problemApiType = ProblemApiType.ENTITY_IN_USE;
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemApi problemApi = instanceProblemApi(status, problemApiType, ex.getMessage())
				.userMessage(ex.getMessage())
				.build();

		return handleExceptionInternal(ex, problemApi, new HttpHeaders(), status, req);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest req,
			HttpHeaders headers, HttpStatus status) {
		ProblemApiType problemApiType = ProblemApiType.METHOD_ARGUMENT_TYPE_MISMATCH;
		String detail = String.format(
				"O parâmetro da URL '%s' recebeu o valor '%s', que é um tipo inválido. Informe um valor compatível com o tipo %s",
				ex.getName(), ex.getValue(), ex.getRequiredType().getName());

		ProblemApi problemApi = instanceProblemApi(status, problemApiType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problemApi, headers, status, req);

	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, request,
					headers, status);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
			HttpHeaders headers, HttpStatus status, WebRequest req) {

		ProblemApiType problemApiType = ProblemApiType.INVALID_DATA;
		String detail = "Um ou mais campos estão inválidos. Faça o preechimento correto e tente novamente.";

		List<ProblemApi.Object> problemsObj = bindingResult
				.getAllErrors()
				.stream()
				.map(objProblem -> {
					String msg = messageSource.getMessage(objProblem,
							LocaleContextHolder.getLocale());

					String name = objProblem.getObjectName();

					if (objProblem instanceof FieldError) {
						name = ((FieldError) objProblem).getField();
					}

					return ProblemApi.Object.builder()
							.name(name)
							.userMessage(msg)
							.build();

				}).collect(Collectors.toList());

		ProblemApi problemApi = instanceProblemApi(status, problemApiType, detail)
				.userMessage(detail)
				.objects(problemsObj)
				.build();

		return handleExceptionInternal(ex, problemApi, headers, status, req);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}

	public ProblemApi.ProblemApiBuilder instanceProblemApi(HttpStatus status, ProblemApiType problemApiType,
			String detail) {
		return ProblemApi.builder()
				.status(status.value())
				.type(problemApiType.getUri())
				.title(problemApiType.getTitle())
				.detail(detail)
				.timestamp(OffsetDateTime.now());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemApiType problemApiType = ProblemApiType.MEDIA_TYPE_NOT_SUPPORTED;
		ProblemApi problemApi = instanceProblemApi(status, problemApiType, ex.getMessage())
				.userMessage("Tipo de mídia não suportada. Insira no formato correto (JSON) e tente novamente.")
				.build();

		return handleExceptionInternal(ex, problemApi, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
			HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = ProblemApi.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.userMessage(ERROR_MSG_GENERIC)
					.build();
		} else if (body instanceof String) {
			body = ProblemApi.builder()
					.title((String) body)
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.userMessage(ERROR_MSG_GENERIC)
					.build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
}