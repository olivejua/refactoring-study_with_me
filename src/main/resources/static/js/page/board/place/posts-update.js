const oEditor = [];
const update_main = {
     init : function () {
         var _this = this;
        _this.loadEditorFrame();
        _this.loadAddressApi();

        $('#btn-add-link').on('click', function () {
            _this.addLink();
        });

        $('#thumbnail').on('change', function (e) {
            _this.createdPreviewThumbnail(e);
        })
     },
    loadEditorFrame : function () {
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
    loadAddressApi : function () {
        //주소 api
        $("#postcodify_search_button").postcodifyPopUp();
    },
    addLink : function () {
        const addedLink = $('#add-link').val();
        const addedElements =
            `<div name="div-link">
                <button type="button" class="btn btn-default" onclick="removeLink(this)">
                    <img class="add-icon" src="/img/place/minus-icon.png" />
                </button>
                <label name="link">${addedLink}</label>
            </div>`;

        $('#div-linksBox').prepend($(addedElements));
        $('#add-link').val("");
    },
    createdPreviewThumbnail : function (e) {
        const reader = new FileReader();

        $('#preview-thumbnail').empty();

        reader.onload = function (event) {
            const img = document.createElement('img');
            img.setAttribute('src', event.target.result);
            document.querySelector('div#preview-thumbnail').appendChild(img);
        };

        reader.readAsDataURL(e.target.files[0]);
        $('#preview-thumbnail').css('display', 'block');
    }
};

var setConditionExplanation = function () {
    oEditor.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
};

var removeLink = function (target) {
    $(target).closest('div[name=div-link]').remove();
};

update_main.init();

