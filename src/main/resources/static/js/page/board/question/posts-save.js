//전역변수

var oEditor = [];
var save_main = {
     init : function () {
         var _this = this;
        _this.setEditorFrame();
     },
    setEditorFrame : function () {
        //스마트에디터 프레임생성
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditor,
            elPlaceHolder: "content",
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
};

var setConditionExplanation = function () {
    oEditor.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
};

save_main.init();

