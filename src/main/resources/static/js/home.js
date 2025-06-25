function addToFavorites(itemJson) {
    console.log("Adding to favorites:", itemJson);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "favoriteServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("已添加到收藏夹！");
        }
    };
    xhr.send("action=add&productId=" + encodeURIComponent(itemJson.itemId) + "&productName=" + encodeURIComponent(itemJson.itemName) + "&price=" + encodeURIComponent(itemJson.price));
}