window.onload=function () {
        var tabBar_home=document.getElementById("tabBar-home");
        var tabBar_history=document.getElementById("tabBar-history");
        var tabBar_mine=document.getElementById("tabBar-mine");
        var img1=document.getElementById("img1")
        var img2=document.getElementById("img2")
        var img3=document.getElementById("img3")
        var home=document.getElementById("home")
        var history=document.getElementById("history")
        var mine=document.getElementById("mine")
        tabBar_home.onclick=function () {
            img1.src="images/home_click.png"
            img2.src="images/history.png"
            img3.src="images/mine.png"
            home.style.visibility="visible"
            history.style.visibility="hidden"
            mine.style.visibility="hidden"
        }

        tabBar_history.onclick=function () {
            img2.src="images/history_click.png"
            img1.src="images/home.png"
            img3.src="images/mine.png"
            home.style.visibility="hidden"
            history.style.visibility="visible"
            mine.style.visibility="hidden"
        }

        tabBar_mine.onclick=function () {
            img1.src="images/home.png"
            img3.src="images/mine_click.png"
            img2.src="images/history.png"
            home.style.visibility="hidden"
            history.style.visibility="hidden"
            mine.style.visibility="visible"
        }

    }
