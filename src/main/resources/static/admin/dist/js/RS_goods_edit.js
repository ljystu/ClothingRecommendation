var editorD;

$('#saveButton').click(function () {
    var goodsId = $('#goodsId').val();
    var goodsCategoryId = $('#levelOne option:selected').val();
    console.log(goodsCategoryId)
    var goodsName = $('#goodsName').val();
    var sellingPrice = $('#sellingPrice').val();
    var goodsSellStatus = $("input[name='goodsSellStatus']:checked").val();
    var goodsCoverImg = $('#goodsCoverImg')[0].src;
    if (isNull(goodsCategoryId)) {
        swal("请选择分类", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsName)) {
        swal("请输入商品名称", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsName, 100)) {
        swal("请输入商品名称", {
            icon: "error",
        });
        return;
    }

    if (isNull(sellingPrice) || sellingPrice < 1) {
        swal("请输入商品售卖价", {
            icon: "error",
        });
        return;
    }

    if (isNull(goodsSellStatus)) {
        swal("请选择上架状态", {
            icon: "error",
        });
        return;
    }

    var url = '/admin/goods/save';
    var swlMessage = '保存成功';
    var data = {
        "goodsName": goodsName,
        "goodsCategoryId": goodsCategoryId,
        "sellingPrice": sellingPrice,
        "goodsCoverImg": goodsCoverImg,
        "goodsSellStatus": goodsSellStatus,
    };
    if (goodsId > 0) {
        url = '/admin/goods/update';
        swlMessage = '修改成功';
        data = {
            "goodsId": goodsId,
            "goodsName": goodsName,
            "goodsCategoryId": goodsCategoryId,
            "sellingPrice": sellingPrice,
            "goodsCoverImg": goodsCoverImg,
            "goodsSellStatus": goodsSellStatus,
        };
    }
    console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#1baeae',
                    confirmButtonText: '返回商品列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/goods";
                })
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});

$('#cancelButton').click(function () {
    window.location.href = "/admin/goods";
});

$('#levelOne').on('change', function () {
    $.ajax({
        url: '/admin/categories/listForSelect?categoryId=' + $(this).val(),
        type: 'GET',
        success: function (result) {
            if (result.resultCode == 200) {
                var levelOneSelect = '';
                var firstLevelCategories = result.data.firstLevelCategories;
                // var length3 = firstLevelCategories.length;
                // for (var i = 0; i < length3; i++) {
                //     levelOneSelect += '<option value=\"' + firstLevelCategories[i].categoryId + '\">' + firstLevelCategories[i].categoryName + '</option>';
                // }
                // levelOneSelect += '<option value=\"' + firstLevelCategories.categoryId + '\">' + firstLevelCategories.categoryName + '</option>';
                // $('#levelOne').html(levelOneSelect);

            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});

$('#recommend').click(function () {
    var goodsId = $('#goodsId').val();
    var goodsCategoryId = $('#levelOne option:selected').val();
    console.log(goodsCategoryId)
    var goodsName = $('#goodsName').val();
    var sellingPrice = $('#sellingPrice').val();
    var goodsSellStatus = $("input[name='goodsSellStatus']:checked").val();
    var goodsCoverImg = $('#goodsCoverImg')[0].src;
    if (isNull(goodsCategoryId)) {
        swal("请选择分类", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsName)) {
        swal("请输入商品名称", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsName, 100)) {
        swal("请输入商品名称", {
            icon: "error",
        });
        return;
    }

    if (isNull(sellingPrice) || sellingPrice < 1) {
        swal("请输入商品售卖价", {
            icon: "error",
        });
        return;
    }

    if (isNull(goodsSellStatus)) {
        swal("请选择上架状态", {
            icon: "error",
        });
        return;
    }

    var url = '/admin/goods/save';
    var swlMessage = '保存成功';
    var data = {
        "goodsName": goodsName,
        "goodsCategoryId": goodsCategoryId,
        "sellingPrice": sellingPrice,
        "goodsCoverImg": goodsCoverImg,
        "goodsSellStatus": goodsSellStatus,
    };
    if (goodsId > 0) {
        url = '/admin/goods/update';
        swlMessage = '修改成功';
        data = {
            "goodsId": goodsId,
            "goodsName": goodsName,
            "goodsCategoryId": goodsCategoryId,
            "sellingPrice": sellingPrice,
            "goodsCoverImg": goodsCoverImg,
            "goodsSellStatus": goodsSellStatus,
        };
    }
    console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#1baeae',
                    confirmButtonText: '返回商品列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/goods";
                })
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});
