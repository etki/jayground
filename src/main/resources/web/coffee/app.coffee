SELECTORS =
  FORM: 'form'
  REQUEST_PARAMETER_INPUT: '[name="request-parameter"]'
  REQUEST_METHOD_SELECT: '[name="request-method"]'
  REQUEST_BODY_TEXTAREA: '[name="request-body"]'
  RESULT_BOX: '#result-box'
  
jQuery(
  ($) ->
    $FORM = $(SELECTORS.FORM)
    $REQUEST_PARAMETER_INPUT = $(SELECTORS.REQUEST_PARAMETER_INPUT)
    $REQUEST_METHOD_SELECT = $(SELECTORS.REQUEST_METHOD_SELECT)
    $REQUEST_BODY_TEXTAREA = $(SELECTORS.REQUEST_BODY_TEXTAREA)
    $RESULT_BOX = $(SELECTORS.RESULT_BOX)
    URI_PREFIX = $FORM.attr('action')
    
    prepareForm = () ->
      $selectedOption = $REQUEST_METHOD_SELECT.find(':selected')
      $REQUEST_PARAMETER_INPUT.prop('disabled', not $selectedOption.data('requires-get-parameter'))
      $REQUEST_BODY_TEXTAREA.prop('disabled', not $selectedOption.data('requires-request-body'))

    prepareForm()
    
    $REQUEST_METHOD_SELECT.on('change', prepareForm)
      
    updateResultBox = (lines...) ->
      $RESULT_BOX.text($RESULT_BOX.text() + line + "\r\n") for line in lines
    
    cleanResultBox = () ->
      $RESULT_BOX.text ''
    
    $FORM.on('submit', (e) ->
      isValid = yes
      $FORM.find('.has-error').removeClass('has-error')
      cleanResultBox()
      
      $selectedOption = $REQUEST_METHOD_SELECT.find(':selected')
      e.preventDefault()
      e.stopPropagation()
      method = $selectedOption.data('method')
      uri = URI_PREFIX + $selectedOption.data('uri')
      if $selectedOption.data('requires-get-parameter')
        requestParameter = $REQUEST_PARAMETER_INPUT.val()
        if not requestParameter
          isValid = no
          $REQUEST_PARAMETER_INPUT.parent().addClass('has-error')
        else
          uri += '/' + requestParameter
      updateResultBox 'запрос:', method + ' ' + uri, ''
      requestBody = null
      if not (method.toLowerCase() is 'get') and $selectedOption.data('requires-request-body')
        requestBody = $REQUEST_BODY_TEXTAREA.val()
        if requestBody
          updateResultBox 'тело запроса:', requestBody, ''
        else
          isValid = no
          $REQUEST_BODY_TEXTAREA.parent().addClass('has-error')
      
      if not isValid
        return no
      
      $.ajax(
        url: uri
        method: method
        dataType: 'html'
        data: requestBody
        contentType: 'application/json; charset=utf-8',
      )
        .done((data) -> updateResultBox 'Ответ: ', data)
        .fail((jqXHR) ->
          message = 'Неудачный запрос (%s %s)'
            .replace('%s', jqXHR.status)
            .replace('%s', jqXHR.statusText)
          updateResultBox message, jqXHR.responseText
        )
    )
)

