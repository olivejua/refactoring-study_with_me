var oEditor = [];
var update_main = {
    init : function () {
        var _this = this;

        _this.setEditorFrame();
        _this.setConditionPeriod($('#startDate').attr('name'), $('#endDate').attr('name'));

        $('#btn-add-language').on('click', function () {
            _this.add_language_lumps();
        });

        $(document).on('click', '.delete-lumps', function (e) {
            _this.delete_lumps(e.target);
        });

        $('#checkbox-nolimit').on('click', function () {
            _this.click_isLimit();
        });

    },
    setEditorFrame : function () {
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditor,
            elPlaceHolder: "conditionExplanation",
            sSkinURI: "/api/se2/SmartEditor2Skin.html",
            htParams : {
                // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
                bUseToolbar : true,
                // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
                bUseVerticalResizer : true,
                // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
                bUseModeChanger : true,
            }
        });
    },
    setConditionPeriod : function(initStartDate, initEndDate) {
        //모든 datepicker에 대한 공통 옵션 설정
        $.datepicker.setDefaults({
            dateFormat: 'yy-mm-dd' //Input Display Format 변경
            ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
            ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
            ,changeYear: true //콤보박스에서 년 선택 가능
            ,changeMonth: true //콤보박스에서 월 선택 가능
            ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시
            ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
            ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
            ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트
            ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
            ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
            ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
            ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
            ,minDate: "-1M" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
            ,maxDate: "+1Y" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)
        });

        //input을 datepicker로 선언
        $("#startDate").datepicker();
        $("#endDate").datepicker();

        // //From의 초기값을 오늘 날짜로 설정
        $('#startDate').datepicker('setDate', initStartDate); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
        // //To의 초기값을 내일로 설정
        $('#endDate').datepicker('setDate', initEndDate); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
    },
    click_isLimit : function () {
        if($('#checkbox-nolimit').is(':checked')) {
            $('#conditionCapacity').prop('disabled', true);
        } else {
            $('#conditionCapacity').prop('disabled', false);
        }
    },
    add_language_lumps : function () {
        const language = $('input[name=input-add-language]').val();

        $('#languages-box').append(`<span class="language-lumps">${language}<button type="button" class="delete-lumps">X</button></span>`);
        $('input[name=input-add-language]').val("");
    },
    delete_lumps : function (target) {
        $(target).closest('.language-lumps').remove();
    }
};

var setConditionExplanation = function () {
    oEditor.getById["conditionExplanation"].exec("UPDATE_CONTENTS_FIELD", []);
};

update_main.init();

