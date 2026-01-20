package com.example.githubrepoapp.analytics

enum class LogEvent(eventName: String) {
    BUTTON_CLICK("button_click"),
    REPO_ITEM_CLICK("repo_item_click"),

}

enum class LogParamName(paramName: String) {
    BUTTON_NAME("button_name"),
    REPO_ITEM_NAME("repo_item_name"),
    REPO_ITEM_OWNER("repo_item_owner")
}

enum class LogParamValue(value: String) {
    LOGIN_BUTTON("login_button")
}