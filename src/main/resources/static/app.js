/**
 * Created by villehietanen on 26/01/17.
 */
var stompClient = null;

function init() {
    connect();
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#log").html("");
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/errors', function (log) {
            clearView()
            showLogs(JSON.parse(log.body).content);
        });
    });
}

function clearView() {
    $("#log").empty();
}

function showLogs(message) {
    $("#log").append(message);
}

