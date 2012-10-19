package org.zkoss.zkmvc.core.backup;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.support.HandlerMethodResolver;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
//import org.springframework.web.servlet.mvc.annotation.ServletAnnotationMappingUtils;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.util.UrlPathHelper;
import org.zkoss.zkmvc.core.RequestKeys;

@Deprecated
public class ViewModelAnnotationMethodHandlerAdapter extends AnnotationMethodHandlerAdapter {

	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ModelAndView mav = super.handle(request, response, handler);
		String basePath = urlPathHelper.getLookupPathForRequest(request);
		mav.getModelMap().put(RequestKeys.BASE_PATH, basePath);
		return mav;
		
	}
	@Override
	protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(true){
			return super.invokeHandlerMethod(request, response, handler);
		}
		return null;
//		ServletHandlerMethodResolver methodResolver = getMethodResolver(handler);
//		Method handlerMethod = methodResolver.resolveHandlerMethod(request);
//		ServletHandlerMethodInvoker methodInvoker = new ServletHandlerMethodInvoker(methodResolver);
//		ServletWebRequest webRequest = new ServletWebRequest(request, response);
//		ExtendedModelMap implicitModel = new BindingAwareModelMap();
//
//		Object result = methodInvoker.invokeHandlerMethod(handlerMethod, handler, webRequest, implicitModel);
//		ModelAndView mav =
//				methodInvoker.getModelAndView(handlerMethod, handler.getClass(), result, implicitModel, webRequest);
//		methodInvoker.updateModelAttributes(handler, (mav != null ? mav.getModel() : null), implicitModel, webRequest);
//		return mav;
	}
	
//	
//	/*********************************/
//	/**
//	 * Servlet-specific subclass of {@link HandlerMethodResolver}.
//	 */
//	private class ServletHandlerMethodResolver extends HandlerMethodResolver {
//
//		private final Map<Method, RequestMappingInfo> mappings = new HashMap<Method, RequestMappingInfo>();
//
//		private ServletHandlerMethodResolver(Class<?> handlerType) {
//			init(handlerType);
//		}
//
//		@Override
//		protected boolean isHandlerMethod(Method method) {
//			if (this.mappings.containsKey(method)) {
//				return true;
//			}
//			RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
//			if (mapping != null) {
//				String[] patterns = mapping.value();
//				RequestMethod[] methods = new RequestMethod[0];
//				String[] params = new String[0];
//				String[] headers = new String[0];
//				if (!hasTypeLevelMapping() || !Arrays.equals(mapping.method(), getTypeLevelMapping().method())) {
//					methods = mapping.method();
//				}
//				if (!hasTypeLevelMapping() || !Arrays.equals(mapping.params(), getTypeLevelMapping().params())) {
//					params = mapping.params();
//				}
//				if (!hasTypeLevelMapping() || !Arrays.equals(mapping.headers(), getTypeLevelMapping().headers())) {
//					headers = mapping.headers();
//				}
//				RequestMappingInfo mappingInfo = new RequestMappingInfo(patterns, methods, params, headers);
//				this.mappings.put(method, mappingInfo);
//				return true;
//			}
//			return false;
//		}
//
//		public Method resolveHandlerMethod(HttpServletRequest request) throws ServletException {
//			String lookupPath = urlPathHelper.getLookupPathForRequest(request);
//			Comparator<String> pathComparator = pathMatcher.getPatternComparator(lookupPath);
//			Map<RequestSpecificMappingInfo, Method> targetHandlerMethods = new LinkedHashMap<RequestSpecificMappingInfo, Method>();
//			Set<String> allowedMethods = new LinkedHashSet<String>(7);
//			String resolvedMethodName = null;
//			for (Method handlerMethod : getHandlerMethods()) {
//				RequestSpecificMappingInfo mappingInfo = new RequestSpecificMappingInfo(this.mappings.get(handlerMethod));
//				boolean match = false;
//				if (mappingInfo.hasPatterns()) {
//					for (String pattern : mappingInfo.getPatterns()) {
//						if (!hasTypeLevelMapping() && !pattern.startsWith("/")) {
//							pattern = "/" + pattern;
//						}
//						String combinedPattern = getCombinedPattern(pattern, lookupPath, request);
//						if (combinedPattern != null) {
//							if (mappingInfo.matches(request)) {
//								match = true;
//								mappingInfo.addMatchedPattern(combinedPattern);
//							}
//							else {
//								if (!mappingInfo.matchesRequestMethod(request)) {
//									allowedMethods.addAll(mappingInfo.methodNames());
//								}
//								break;
//							}
//						}
//					}
//					mappingInfo.sortMatchedPatterns(pathComparator);
//				}
//				else {
//					// No paths specified: parameter match sufficient.
//					match = mappingInfo.matches(request);
//					if (match && mappingInfo.getMethodCount() == 0 && mappingInfo.getParamCount() == 0 &&
//							resolvedMethodName != null && !resolvedMethodName.equals(handlerMethod.getName())) {
//						match = false;
//					}
//					else {
//						if (!mappingInfo.matchesRequestMethod(request)) {
//							allowedMethods.addAll(mappingInfo.methodNames());
//						}
//					}
//				}
//				if (match) {
//					Method oldMappedMethod = targetHandlerMethods.put(mappingInfo, handlerMethod);
//					if (oldMappedMethod != null && oldMappedMethod != handlerMethod) {
//						if (methodNameResolver != null && !mappingInfo.hasPatterns()) {
//							if (!oldMappedMethod.getName().equals(handlerMethod.getName())) {
//								if (resolvedMethodName == null) {
//									resolvedMethodName = methodNameResolver.getHandlerMethodName(request);
//								}
//								if (!resolvedMethodName.equals(oldMappedMethod.getName())) {
//									oldMappedMethod = null;
//								}
//								if (!resolvedMethodName.equals(handlerMethod.getName())) {
//									if (oldMappedMethod != null) {
//										targetHandlerMethods.put(mappingInfo, oldMappedMethod);
//										oldMappedMethod = null;
//									}
//									else {
//										targetHandlerMethods.remove(mappingInfo);
//									}
//								}
//							}
//						}
//						if (oldMappedMethod != null) {
//							throw new IllegalStateException(
//									"Ambiguous handler methods mapped for HTTP path '" + lookupPath + "': {" +
//									oldMappedMethod + ", " + handlerMethod +
//									"}. If you intend to handle the same path in multiple methods, then factor " +
//									"them out into a dedicated handler class with that path mapped at the type level!");
//						}
//					}
//				}
//			}
//			if (!targetHandlerMethods.isEmpty()) {
//				List<RequestSpecificMappingInfo> matches = new ArrayList<RequestSpecificMappingInfo>(targetHandlerMethods.keySet());
//				RequestSpecificMappingInfoComparator requestMappingInfoComparator =
//						new RequestSpecificMappingInfoComparator(pathComparator, request);
//				Collections.sort(matches, requestMappingInfoComparator);
//				RequestSpecificMappingInfo bestMappingMatch = matches.get(0);
//				String bestMatchedPath = bestMappingMatch.bestMatchedPattern();
//				if (bestMatchedPath != null) {
//					extractHandlerMethodUriTemplates(bestMatchedPath, lookupPath, request);
//				}
//				return targetHandlerMethods.get(bestMappingMatch);
//			}
//			else {
//				if (!allowedMethods.isEmpty()) {
//					throw new HttpRequestMethodNotSupportedException(request.getMethod(),
//							StringUtils.toStringArray(allowedMethods));
//				}
//				throw new NoSuchRequestHandlingMethodException(lookupPath, request.getMethod(),
//						request.getParameterMap());
//			}
//		}
//
//		/**
//		 * Determines the combined pattern for the given methodLevelPattern and path.
//		 * <p>Uses the following algorithm: <ol>
//		 * <li>If there is a type-level mapping with path information, it is {@linkplain
//		 * PathMatcher#combine(String, String) combined} with the method-level pattern.</li>
//		 * <li>If there is a {@linkplain HandlerMapping#BEST_MATCHING_PATTERN_ATTRIBUTE best matching pattern} in the
//		 * request, it is combined with the method-level pattern.</li>
//		 * <li>Otherwise, the method-level pattern is returned.</li>
//		 * </ol>
//		 */
//		private String getCombinedPattern(String methodLevelPattern, String lookupPath, HttpServletRequest request) {
//			if (hasTypeLevelMapping() && (!ObjectUtils.isEmpty(getTypeLevelMapping().value()))) {
//				String[] typeLevelPatterns = getTypeLevelMapping().value();
//				for (String typeLevelPattern : typeLevelPatterns) {
//					if (!typeLevelPattern.startsWith("/")) {
//						typeLevelPattern = "/" + typeLevelPattern;
//					}
//					String combinedPattern = pathMatcher.combine(typeLevelPattern, methodLevelPattern);
//					if (isPathMatchInternal(combinedPattern, lookupPath)) {
//						return combinedPattern;
//					}
//				}
//				return null;
//			}
//			String bestMatchingPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
//			if (StringUtils.hasText(bestMatchingPattern) && bestMatchingPattern.endsWith("*")) {
//				String combinedPattern = pathMatcher.combine(bestMatchingPattern, methodLevelPattern);
//				if (!combinedPattern.equals(bestMatchingPattern) &&
//						(isPathMatchInternal(combinedPattern, lookupPath))) {
//					return combinedPattern;
//				}
//			}
//			if (isPathMatchInternal(methodLevelPattern, lookupPath)) {
//				return methodLevelPattern;
//			}
//			return null;
//		}
//
//		private boolean isPathMatchInternal(String pattern, String lookupPath) {
//			if (pattern.equals(lookupPath) || pathMatcher.match(pattern, lookupPath)) {
//				return true;
//			}
//			boolean hasSuffix = pattern.indexOf('.') != -1;
//			if (!hasSuffix && pathMatcher.match(pattern + ".*", lookupPath)) {
//				return true;
//			}
//			boolean endsWithSlash = pattern.endsWith("/");
//			if (!endsWithSlash && pathMatcher.match(pattern + "/", lookupPath)) {
//				return true;
//			}
//			return false;
//		}
//
//		@SuppressWarnings("unchecked")
//		private void extractHandlerMethodUriTemplates(String mappedPattern,
//				String lookupPath,
//				HttpServletRequest request) {
//
//			Map<String, String> variables =
//					(Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//
//			int patternVariableCount = StringUtils.countOccurrencesOf(mappedPattern, "{");
//			
//			if ( (variables == null || patternVariableCount != variables.size())  
//					&& pathMatcher.match(mappedPattern, lookupPath)) {
//				variables = pathMatcher.extractUriTemplateVariables(mappedPattern, lookupPath);
//				request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, variables);
//			}
//		}
//	}
//	
//	/**
//	 * Holder for request mapping metadata.
//	 */
//	static class RequestMappingInfo {
//
//		private final String[] patterns;
//
//		private final RequestMethod[] methods;
//
//		private final String[] params;
//
//		private final String[] headers;
//
//		RequestMappingInfo(String[] patterns, RequestMethod[] methods, String[] params, String[] headers) {
//			this.patterns = patterns != null ? patterns : new String[0];
//			this.methods = methods != null ? methods : new RequestMethod[0];
//			this.params = params != null ? params : new String[0];
//			this.headers = headers != null ? headers : new String[0];
//		}
//
//		public boolean hasPatterns() {
//			return patterns.length > 0;
//		}
//
//		public String[] getPatterns() {
//			return patterns;
//		}
//
//		public int getMethodCount() {
//			return methods.length;
//		}
//
//		public int getParamCount() {
//			return params.length;
//		}
//
//		public int getHeaderCount() {
//			return headers.length;
//		}
//
//		public boolean matches(HttpServletRequest request) {
//			return matchesRequestMethod(request) && matchesParameters(request) && matchesHeaders(request);
//		}
//
//		public boolean matchesHeaders(HttpServletRequest request) {
//			return ServletAnnotationMappingUtils.checkHeaders(this.headers, request);
//		}
//
//		public boolean matchesParameters(HttpServletRequest request) {
//			return ServletAnnotationMappingUtils.checkParameters(this.params, request);
//		}
//
//		public boolean matchesRequestMethod(HttpServletRequest request) {
//			return ServletAnnotationMappingUtils.checkRequestMethod(this.methods, request);
//		}
//
//		public Set<String> methodNames() {
//			Set<String> methodNames = new LinkedHashSet<String>(methods.length);
//			for (RequestMethod method : methods) {
//				methodNames.add(method.name());
//			}
//			return methodNames;
//		}
//
//		@Override
//		public boolean equals(Object obj) {
//			RequestMappingInfo other = (RequestMappingInfo) obj;
//			return (Arrays.equals(this.patterns, other.patterns) && Arrays.equals(this.methods, other.methods) &&
//					Arrays.equals(this.params, other.params) && Arrays.equals(this.headers, other.headers));
//		}
//
//		@Override
//		public int hashCode() {
//			return (Arrays.hashCode(this.patterns) * 23 + Arrays.hashCode(this.methods) * 29 +
//					Arrays.hashCode(this.params) * 31 + Arrays.hashCode(this.headers));
//		}
//
//		@Override
//		public String toString() {
//			StringBuilder builder = new StringBuilder();
//			builder.append(Arrays.asList(patterns));
//			if (methods.length > 0) {
//				builder.append(',');
//				builder.append(Arrays.asList(methods));
//			}
//			if (headers.length > 0) {
//				builder.append(',');
//				builder.append(Arrays.asList(headers));
//			}
//			if (params.length > 0) {
//				builder.append(',');
//				builder.append(Arrays.asList(params));
//			}
//			return builder.toString();
//		}
//	}
//
//
//	/**
//	 * Subclass of {@link RequestMappingInfo} that holds request-specific data.
//	 */
//	static class RequestSpecificMappingInfo extends RequestMappingInfo {
//
//		private final List<String> matchedPatterns = new ArrayList<String>();
//
//		RequestSpecificMappingInfo(String[] patterns, RequestMethod[] methods, String[] params, String[] headers) {
//			super(patterns, methods, params, headers);
//		}
//
//		RequestSpecificMappingInfo(RequestMappingInfo other) {
//			super(other.patterns, other.methods, other.params, other.headers);
//		}
//
//		public void addMatchedPattern(String matchedPattern) {
//			matchedPatterns.add(matchedPattern);
//		}
//
//		public void sortMatchedPatterns(Comparator<String> pathComparator) {
//			Collections.sort(matchedPatterns, pathComparator);
//		}
//
//		public String bestMatchedPattern() {
//			return (!this.matchedPatterns.isEmpty() ? this.matchedPatterns.get(0) : null);
//		}
//	}
//
//
//	/**
//	 * Comparator capable of sorting {@link RequestSpecificMappingInfo}s (RHIs) so that
//	 * sorting a list with this comparator will result in:
//	 * <ul>
//	 * <li>RHIs with {@linkplain AnnotationMethodHandlerAdapter.RequestSpecificMappingInfo#matchedPatterns better matched paths}
//	 * take prescedence over those with a weaker match (as expressed by the {@linkplain PathMatcher#getPatternComparator(String)
//	 * path pattern comparator}.) Typically, this means that patterns without wild cards and uri templates
//	 * will be ordered before those without.</li>
//	 * <li>RHIs with one single {@linkplain RequestMappingInfo#methods request method} will be
//	 * ordered before those without a method, or with more than one method.</li>
//	 * <li>RHIs with more {@linkplain RequestMappingInfo#params request parameters} will be ordered
//	 * before those with less parameters</li>
//	 * </ol>
//	 */
//	static class RequestSpecificMappingInfoComparator implements Comparator<RequestSpecificMappingInfo> {
//
//		private final Comparator<String> pathComparator;
//
//		private final ServerHttpRequest request;
//
//		RequestSpecificMappingInfoComparator(Comparator<String> pathComparator, HttpServletRequest request) {
//			this.pathComparator = pathComparator;
//			this.request = new ServletServerHttpRequest(request);
//		}
//
//		public int compare(RequestSpecificMappingInfo info1, RequestSpecificMappingInfo info2) {
//			int pathComparison = pathComparator.compare(info1.bestMatchedPattern(), info2.bestMatchedPattern());
//			if (pathComparison != 0) {
//				return pathComparison;
//			}
//			int info1ParamCount = info1.getParamCount();
//			int info2ParamCount = info2.getParamCount();
//			if (info1ParamCount != info2ParamCount) {
//				return info2ParamCount - info1ParamCount;
//			}
//			int info1HeaderCount = info1.getHeaderCount();
//			int info2HeaderCount = info2.getHeaderCount();
//			if (info1HeaderCount != info2HeaderCount) {
//				return info2HeaderCount - info1HeaderCount;
//			}
//			int acceptComparison = compareAcceptHeaders(info1, info2);
//			if (acceptComparison != 0) {
//				return acceptComparison;
//			}
//			int info1MethodCount = info1.getMethodCount();
//			int info2MethodCount = info2.getMethodCount();
//			if (info1MethodCount == 0 && info2MethodCount > 0) {
//				return 1;
//			}
//			else if (info2MethodCount == 0 && info1MethodCount > 0) {
//				return -1;
//			}
//			else if (info1MethodCount == 1 & info2MethodCount > 1) {
//				return -1;
//			}
//			else if (info2MethodCount == 1 & info1MethodCount > 1) {
//				return 1;
//			}
//			return 0;
//		}
//
//		private int compareAcceptHeaders(RequestMappingInfo info1, RequestMappingInfo info2) {
//			List<MediaType> requestAccepts = request.getHeaders().getAccept();
//			MediaType.sortByQualityValue(requestAccepts);
//			
//			List<MediaType> info1Accepts = getAcceptHeaderValue(info1);
//			List<MediaType> info2Accepts = getAcceptHeaderValue(info2);
//
//			for (MediaType requestAccept : requestAccepts) {
//				int pos1 = indexOfIncluded(info1Accepts, requestAccept);
//				int pos2 = indexOfIncluded(info2Accepts, requestAccept);
//				if (pos1 != pos2) {
//					return pos2 - pos1;
//				}
//			}
//			return 0;
//		}
//
//		private int indexOfIncluded(List<MediaType> infoAccepts, MediaType requestAccept) {
//			for (int i = 0; i < infoAccepts.size(); i++) {
//				MediaType info1Accept = infoAccepts.get(i);
//				if (requestAccept.includes(info1Accept)) {
//					return i;
//				}
//			}
//			return -1;
//		}
//
//		private List<MediaType> getAcceptHeaderValue(RequestMappingInfo info) {
//			for (String header : info.headers) {
//				int separator = header.indexOf('=');
//				if (separator != -1) {
//					String key = header.substring(0, separator);
//					String value = header.substring(separator + 1);
//					if ("Accept".equalsIgnoreCase(key)) {
//						return MediaType.parseMediaTypes(value);
//					}
//				}
//			}
//			return Collections.emptyList();
//		}
//	}
}
