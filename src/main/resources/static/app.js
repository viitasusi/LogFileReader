/**
 * Created by villehietanen on 26/01/17.
 */
var stompClient = null;

function init() {
    connect();
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/errors', function (msg) {
            showLogs(JSON.parse(JSON.parse(msg.body).content));
        });
    });
}

function clearView() {
    $("#info-messages").empty();
    $("#warning-messages").empty();
    $("#error-messages").empty();
}

function showLogs(message) {
    clearView();
    $("#info-messages").append(message.info);
    $("#warning-messages").append(message.warnings);
    $("#error-messages").append(message.errors);
}

