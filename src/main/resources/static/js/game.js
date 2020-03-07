// 用于form表单提交后不跳转
function startGenerate() {

    var gameName =$("#gameName").val();

    var tbody = "<table style='margin: 0 auto' cellpadding='5' cellspacing='1' width='80%' border=1><tr><th>------------空格游戏名的结果------------</th></tr>";

    $.ajax({
        url: "/game/ok",
        type: 'POST',
        data: JSON.stringify({"name":gameName}),
        dataType:'json',
        contentType:"application/json",
        success: function (result) {


            if (result.code === 200){
                $.each(result.data, function (n, value) {
                    var trs = "";
                    trs += "<tr><td>" + value + "</td></tr>";
                    tbody += trs;

                });
                tbody += "</table>";
                $("#result").html(tbody);

            }else {
                alert(result.msg);
            }


        }
    });
}
