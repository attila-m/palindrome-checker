var stompClient = null;

connect()

function connect() {
    var socket = new SockJS('/message-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            displayMessage(message);
        });
    });
}

function displayMessage(message) {
    var body = JSON.parse(message.body);
    $("#messages").append("<tr><td>"
        + body.content + "</td><td>"
        + body.timestamp + "</td></tr>");
}