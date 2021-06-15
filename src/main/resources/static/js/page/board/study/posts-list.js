const list_main = {
    init           : function () {
        const _this = this;

        _this.loadSearchStatus();

    },
    loadSearchStatus : function () {
        const searchType = $('#searchType').val();
        const keyword = $('#keyword').val();

        if(searchType===undefined || keyword===undefined) {
            return;
        }

        $('#search-category').val(searchType).attr('selected', true);
        $('#search-keyword').val(keyword);
    }
};

list_main.init();