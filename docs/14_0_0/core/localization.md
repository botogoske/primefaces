# Localization

Components may require translations and other settings based on different locales. There are two
different places for localization one for the server and one for the client.  Client side localization
is for JavaScript values that can be localized and server side is for server side components.

## Server Localization
Server localization is handled by include a custom `Messages.properties` file for your language(s)
in the location such as `\org\primefaces\Messages.properties` of your JAR or WAR file.  For example for a German translation
of this file you would include a default `Messages_de.properties` file.

Source code bundles:  [GitHub Server Message Bundle Files](https://github.com/primefaces/primefaces/tree/master/primefaces/src/main/resources/org/primefaces)

### Default Messages.properties

```
primefaces.calendar.DATE_INVALID_RANGE_MESSAGE_ID = {0}: Validation Error: Start date is greater than the end date.
primefaces.calendar.INVALID = {0}: Validation Error: Value is not valid.
primefaces.calendar.MAX_DATE = {0}: Validation Error: Value must be {2} or before.
primefaces.calendar.MIN_DATE = {0}: Validation Error: Value must be {1} or later.
primefaces.calendar.OUT_OF_RANGE = {0}: Validation Error: Value must be between {1} and {2}.
primefaces.captcha.INVALID = {0}: Validation Error: Value is not valid.
primefaces.captcha.INVALID_detail = {0}: ''{1}'' does not match the displayed text.
primefaces.data.EMPTY_MESSAGE = No records found.
primefaces.datatable.SORT_ASC = Ascending
primefaces.datatable.SORT_DESC = Descending
primefaces.datatable.SORT_LABEL = Sort
primefaces.fileupload.CLAM_AV_FILE = ClamAV has identified file ''{0}'' as potentially dangerous with message ''{1}''
primefaces.fileupload.VIRUS_TOTAL_FILE = Virus Total has identified file ''{0}'' potentially dangerous
primefaces.password.INVALID_MATCH = {0}: Validation Error.
primefaces.password.INVALID_MATCH_detail = {0} should match with {1}.
primefaces.slider.OUT_OF_RANGE = {0}: Validation Error: Value is out of range.
primefaces.tree.REQUIRED = {0}: Validation Error.
primefaces.tree.REQUIRED_detail = Selection is required.

```

## Client Localization
This is handled with a client side API called PrimeFaces Locales. A client side locale is basically 
a JavaScript object with various settings, en_US is the default locale provided out of the box. 
In case you need to support another locale, settings should be extended with the new information.

Source code scripts: [GitHub Client Locale Javascript Files](https://github.com/primefaces/primefaces/tree/master/primefaces/src/main/resources/META-INF/resources/primefaces/locales)

PrimeLocale code scripts: [PrimeLocale](https://github.com/primefaces/primelocale)

### Manual Locale Loading
If you know the locale you want added to your page and want to include one of the built in locales from PrimeFaces
just add the appropriate script to your page. For example to load the Dutch language add the following:

```xml
<h:outputScript name="locales/locale-nl.js" library="primefaces"/>
```

### Automatic Locale Loading
Using _primefaces.CLIENT_SIDE_LOCALISATION_ global setting will automatically detect your locale and try to load the 
appropriate locale file from `"locales/locale-" + locale.getLanguage() + ".js"`.

```xml
<context-param>
    <param-name>primefaces.CLIENT_SIDE_LOCALISATION</param-name>
    <param-value>true</param-value>
</context-param>
```

### Default Locale
Here is the list of all key-value pairs for en_US locale that is provided by PrimeFaces. DateTime
related properties are utilized by components such as calendar and schedule. If you are using Client
Side Validation, messages property is used as the bundle for the locale.

```js
if (window.PrimeFaces) {
  /** English (from PrimeLocale)*/
  PrimeFaces.locales["en"] = {
    startsWith: "Starts with",
    contains: "Contains",
    notContains: "Not contains",
    endsWith: "Ends with",
    equals: "Equals",
    notEquals: "Not equals",
    noFilter: "No Filter",
    filter: "Filter",
    lt: "Less than",
    lte: "Less than or equal to",
    gt: "Greater than",
    gte: "Greater than or equal to",
    dateIs: "Date is",
    dateIsNot: "Date is not",
    dateBefore: "Date is before",
    dateAfter: "Date is after",
    custom: "Custom",
    clear: "Clear",
    apply: "Apply",
    matchAll: "Match All",
    matchAny: "Match Any",
    addRule: "Add Rule",
    removeRule: "Remove Rule",
    accept: "Yes",
    reject: "No",
    choose: "Choose",
    upload: "Upload",
    cancel: "Cancel",
    completed: "Completed",
    pending: "Pending",
    dayNames: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
    dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
    dayNamesMin: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"],
    monthNames: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
    monthNamesShort: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
    chooseYear: "Choose Year",
    chooseMonth: "Choose Month",
    chooseDate: "Choose Date",
    prevDecade: "Previous Decade",
    nextDecade: "Next Decade",
    prevYear: "Previous Year",
    nextYear: "Next Year",
    prevMonth: "Previous Month",
    nextMonth: "Next Month",
    prevHour: "Previous Hour",
    nextHour: "Next Hour",
    prevMinute: "Previous Minute",
    nextMinute: "Next Minute",
    prevSecond: "Previous Second",
    nextSecond: "Next Second",
    am: "AM",
    pm: "PM",
    today: "Today",
    now: "Now",
    weekHeader: "Wk",
    firstDayOfWeek: 0,
    showMonthAfterYear: false,
    dateFormat: "mm/dd/yy",
    weak: "Weak",
    medium: "Medium",
    strong: "Strong",
    passwordPrompt: "Enter a password",
    emptyFilterMessage: "No results found",
    searchMessage: "{0} results are available",
    selectionMessage: "{0} items selected",
    emptySelectionMessage: "No selected item",
    emptySearchMessage: "No results found",
    emptyMessage: "No available options",
    aria: {
      trueLabel: "True",
      falseLabel: "False",
      nullLabel: "Not Selected",
      star: "1 star",
      stars: "{star} stars",
      selectAll: "All items selected",
      unselectAll: "All items unselected",
      close: "Close",
      previous: "Previous",
      next: "Next",
      navigation: "Navigation",
      scrollTop: "Scroll Top",
      moveTop: "Move Top",
      moveUp: "Move Up",
      moveDown: "Move Down",
      moveBottom: "Move Bottom",
      moveToTarget: "Move to Target",
      moveToSource: "Move to Source",
      moveAllToTarget: "Move All to Target",
      moveAllToSource: "Move All to Source",
      pageLabel: "Page {page}",
      firstPageLabel: "First Page",
      lastPageLabel: "Last Page",
      nextPageLabel: "Next Page",
      previousPageLabel: "Previous Page",
      rowsPerPageLabel: "Rows per page",
      jumpToPageDropdownLabel: "Jump to Page Dropdown",
      jumpToPageInputLabel: "Jump to Page Input",
      selectRow: "Row Selected",
      unselectRow: "Row Unselected",
      expandRow: "Row Expanded",
      collapseRow: "Row Collapsed",
      showFilterMenu: "Show Filter Menu",
      hideFilterMenu: "Hide Filter Menu",
      filterOperator: "Filter Operator",
      filterConstraint: "Filter Constraint",
      editRow: "Edit Row",
      saveEdit: "Save Edit",
      cancelEdit: "Cancel Edit",
      listView: "List View",
      gridView: "Grid View",
      slide: "Slide",
      slideNumber: "{slideNumber}",
      zoomImage: "Zoom Image",
      zoomIn: "Zoom In",
      zoomOut: "Zoom Out",
      rotateRight: "Rotate Right",
      rotateLeft: "Rotate Left",
    },
  };

  // custom PF labels
  PrimeFaces.locales["en"] = $.extend(true, {}, PrimeFaces.locales["en"], {
    weekNumberTitle: "W",
    isRTL: false,
    yearSuffix: "",
    timeOnlyTitle: "Only Time",
    timeText: "Time",
    hourText: "Hour",
    minuteText: "Minute",
    secondText: "Second",
    millisecondText: "Millisecond",
    year: "Year",
    month: "Month",
    week: "Week",
    day: "Day",
    list: "Agenda",
    allDayText: "All Day",
    moreLinkText: "More...",
    noEventsText: "No Events",
    unexpectedError: "Unexpected error",
    aria: {
      "datatable.sort.ASC": "activate to sort column ascending",
      "datatable.sort.DESC": "activate to sort column descending",
      "datatable.sort.NONE": "activate to remove sorting on column",
      "colorpicker.OPEN": "Open color picker",
      "colorpicker.CLOSE": "Close color picker",
      "colorpicker.CLEAR": "Clear the selected color",
      "colorpicker.MARKER": "Saturation: {s}. Brightness: {v}.",
      "colorpicker.HUESLIDER": "Hue slider",
      "colorpicker.ALPHASLIDER": "Opacity slider",
      "colorpicker.INPUT": "Color value field",
      "colorpicker.FORMAT": "Color format",
      "colorpicker.SWATCH": "Color swatch",
      "colorpicker.INSTRUCTION": "Saturation and brightness selector. Use up, down, left and right arrow keys to select.",
      "spinner.INCREASE": "Increase Value",
      "spinner.DECREASE": "Decrease Value",
      "switch.ON": "On",
      "switch.OFF": "Off",
      "messages.ERROR": "Error",
      "messages.FATAL": "Fatal",
      "messages.INFO": "Information",
      "messages.WARN": "Warning",
    },
    messages: {
      //optional for Client Side Validation
      "javax.faces.component.UIInput.REQUIRED": "{0}: Validation Error: Value is required.",
      "javax.faces.converter.IntegerConverter.INTEGER": "{2}: '{0}' must be a number consisting of one or more digits.",
      "javax.faces.converter.IntegerConverter.INTEGER_detail": "{2}: '{0}' must be a number between -2147483648 and 2147483647. Example: {1}.",
      "javax.faces.converter.DoubleConverter.DOUBLE": "{2}: '{0}' must be a number consisting of one or more digits.",
      "javax.faces.converter.DoubleConverter.DOUBLE_detail": "{2}: '{0}' must be a number between 4.9E-324 and 1.7976931348623157E308. Example: {1}.",
      "javax.faces.converter.BigDecimalConverter.DECIMAL": "{2}: '{0}' must be a signed decimal number.",
      "javax.faces.converter.BigDecimalConverter.DECIMAL_detail": "{2}: '{0}' must be a signed decimal number consisting of zero or more digits, that may be followed by a decimal point and fraction. Example: {1}.",
      "javax.faces.converter.BigIntegerConverter.BIGINTEGER": "{2}: '{0}' must be a number consisting of one or more digits.",
      "javax.faces.converter.BigIntegerConverter.BIGINTEGER_detail": "{2}: '{0}' must be a number consisting of one or more digits. Example: {1}.",
      "javax.faces.converter.ByteConverter.BYTE": "{2}: '{0}' must be a number between 0 and 255.",
      "javax.faces.converter.ByteConverter.BYTE_detail": "{2}: '{0}' must be a number between 0 and 255. Example: {1}.",
      "javax.faces.converter.CharacterConverter.CHARACTER": "{1}: '{0}' must be a valid character.",
      "javax.faces.converter.CharacterConverter.CHARACTER_detail": "{1}: '{0}' must be a valid ASCII character.",
      "javax.faces.converter.ShortConverter.SHORT": "{2}: '{0}' must be a number consisting of one or more digits.",
      "javax.faces.converter.ShortConverter.SHORT_detail": "{2}: '{0}' must be a number between -32768 and 32767. Example: {1}.",
      "javax.faces.converter.BooleanConverter.BOOLEAN": "{1}: '{0}' must be 'true' or 'false'.",
      "javax.faces.converter.BooleanConverter.BOOLEAN_detail": "{1}: '{0}' must be 'true' or 'false'.  Any value other than 'true' will evaluate to 'false'.",
      "javax.faces.validator.LongRangeValidator.MAXIMUM": "{1}: Validation Error: Value is greater than allowable maximum of '{0}'.",
      "javax.faces.validator.LongRangeValidator.MINIMUM": "{1}: Validation Error: Value is less than allowable minimum of '{0}'.",
      "javax.faces.validator.LongRangeValidator.NOT_IN_RANGE": "{2}: Validation Error: Specified attribute is not between the expected values of {0} and {1}.",
      "javax.faces.validator.LongRangeValidator.TYPE": "{0}: Validation Error: Value is not of the correct type.",
      "javax.faces.validator.DoubleRangeValidator.MAXIMUM": "{1}: Validation Error: Value is greater than allowable maximum of '{0}'.",
      "javax.faces.validator.DoubleRangeValidator.MINIMUM": "{1}: Validation Error: Value is less than allowable minimum of '{0}'.",
      "javax.faces.validator.DoubleRangeValidator.NOT_IN_RANGE": "{2}: Validation Error: Specified attribute is not between the expected values of {0} and {1}.",
      "javax.faces.validator.DoubleRangeValidator.TYPE": "{0}: Validation Error: Value is not of the correct type.",
      "javax.faces.converter.FloatConverter.FLOAT": "{2}: '{0}' must be a number consisting of one or more digits.",
      "javax.faces.converter.FloatConverter.FLOAT_detail": "{2}: '{0}' must be a number between 1.4E-45 and 3.4028235E38  Example: {1}.",
      "javax.faces.converter.DateTimeConverter.DATE": "{2}: '{0}' could not be understood as a date.",
      "javax.faces.converter.DateTimeConverter.DATE_detail": "{2}: '{0}' could not be understood as a date. Example: {1}.",
      "javax.faces.converter.DateTimeConverter.TIME": "{2}: '{0}' could not be understood as a time.",
      "javax.faces.converter.DateTimeConverter.TIME_detail": "{2}: '{0}' could not be understood as a time. Example: {1}.",
      "javax.faces.converter.DateTimeConverter.DATETIME": "{2}: '{0}' could not be understood as a date and time.",
      "javax.faces.converter.DateTimeConverter.DATETIME_detail": "{2}: '{0}' could not be understood as a date and time. Example: {1}.",
      "javax.faces.converter.DateTimeConverter.PATTERN_TYPE": "{1}: A 'pattern' or 'type' attribute must be specified to convert the value '{0}'.",
      "javax.faces.converter.NumberConverter.CURRENCY": "{2}: '{0}' could not be understood as a currency value.",
      "javax.faces.converter.NumberConverter.CURRENCY_detail": "{2}: '{0}' could not be understood as a currency value. Example: {1}.",
      "javax.faces.converter.NumberConverter.PERCENT": "{2}: '{0}' could not be understood as a percentage.",
      "javax.faces.converter.NumberConverter.PERCENT_detail": "{2}: '{0}' could not be understood as a percentage. Example: {1}.",
      "javax.faces.converter.NumberConverter.NUMBER": "{2}: '{0}' could not be understood as a number.",
      "javax.faces.converter.NumberConverter.NUMBER_detail": "{2}: '{0}' could not be understood as a number. Example: {1}.",
      "javax.faces.converter.NumberConverter.PATTERN": "{2}: '{0}' could not be understood as a number pattern.",
      "javax.faces.converter.NumberConverter.PATTERN_detail": "{2}: '{0}' could not be understood as a number pattern. Example: {1}.",
      "javax.faces.validator.LengthValidator.MINIMUM": "{1}: Validation Error: Length is less than allowable minimum of '{0}'.",
      "javax.faces.validator.LengthValidator.MAXIMUM": "{1}: Validation Error: Length is greater than allowable maximum of '{0}'.",
      "javax.faces.validator.RegexValidator.PATTERN_NOT_SET": "Regex pattern must be set.",
      "javax.faces.validator.RegexValidator.PATTERN_NOT_SET_detail": "Regex pattern must be set to non-empty value.",
      "javax.faces.validator.RegexValidator.NOT_MATCHED": "Regex Pattern not matched.",
      "javax.faces.validator.RegexValidator.NOT_MATCHED_detail": "Regex pattern of '{0}' not matched.",
      "javax.faces.validator.RegexValidator.MATCH_EXCEPTION": "Error in regular expression.",
      "javax.faces.validator.RegexValidator.MATCH_EXCEPTION_detail": "Error in regular expression, '{0}'.",
      "primefaces.FileValidator.FILE_LIMIT": "Maximum number of files exceeded.",
      "primefaces.FileValidator.FILE_LIMIT_detail": "Maximum number: {0}.",
      "primefaces.FileValidator.ALLOW_TYPES": "Invalid file type.",
      "primefaces.FileValidator.ALLOW_TYPES_detail": "Invalid file type: '{0}'.",
      "primefaces.FileValidator.SIZE_LIMIT": "Invalid file size.",
      "primefaces.FileValidator.SIZE_LIMIT_detail": "File '{0}' must not be larger than {1}.",
      //optional for bean validation integration in client side validation
      "javax.faces.validator.BeanValidator.MESSAGE": "{0}",
      "javax.validation.constraints.AssertFalse.message": "must be false.",
      "javax.validation.constraints.AssertTrue.message": "must be true.",
      "javax.validation.constraints.DecimalMax.message": "must be less than or equal to {0}.",
      "javax.validation.constraints.DecimalMin.message": "must be greater than or equal to {0}.",
      "javax.validation.constraints.Digits.message": "numeric value out of bounds (&lt;{0} digits&gt;.&lt;{1} digits&gt; expected).",
      "javax.validation.constraints.Future.message": "must be in the future.",
      "javax.validation.constraints.Max.message": "must be less than or equal to {0}.",
      "javax.validation.constraints.Min.message": "must be greater than or equal to {0}.",
      "javax.validation.constraints.NotNull.message": "may not be null.",
      "javax.validation.constraints.Null.message": "must be null.",
      "javax.validation.constraints.Past.message": "must be in the past.",
      "javax.validation.constraints.Pattern.message": 'must match "{0}".',
      "javax.validation.constraints.Size.message": "size must be between {0} and {1}.",
    },
  });
  // United States
  PrimeFaces.locales["en_US"] = PrimeFaces.locales["en"];
}

```

### Usage

To add another locale to the API, first create the locale object first with settings and assign it as a
property of [PrimeFaces.locales](../jsdocs/modules/src_PrimeFaces.PrimeFaces.html#locales) JavaScript object such as;

_PrimeFaces.locales['de'] = {//settings}_

It is suggested to put this code in a javascript file and include the file into your pages.


## Right to Left

Right-To-Left language support in short RTL is provided out of the box by a subset of PrimeFaces
components. Any component equipped with _dir_ attribute has the official support and there is also a
global setting to switch to RTL mode globally.

Here is an example of an RTL AccordionPanel enabled via _dir_ setting.

```xhtml
<p:accordionPanel dir="rtl">
    //tabs
</p:accordionPanel>
```
**Global Configuration**
Using _primefaces.DIR_ global setting to rtl instructs PrimeFaces RTL aware components such as
datatable, accordion, tabview, dialog, tree to render in RTL mode.

```xml
<context-param>
    <param-name>primefaces.DIR</param-name>
    <param-value>rtl</param-value>
</context-param>
```
Parameter value can also be an EL expression for dynamic values.

In upcoming PrimeFaces releases, more components will receive built-in RTL support. Until then if
the component you use doesn’t provide it, overriding css and javascript in your application would be
the solution.

