class Player {
  constructor(left, right, ability, name) {
    this.leftKey = left;
    this.rightKey = right;
    this.abilityKey = ability;
    this.name = name;
  }
}
Player.prototype.move = function(keyMap) {
  var messageObj = {type: "move", player: this.name};
  if (keyMap[this.leftKey]) {
    if (!messageObj.hasOwnProperty("direction")) {
      messageObj.direction = "left";
      socket.sendMessage(messageObj);
    }
  }
  if (keyMap[this.rightKey]) {
    if (!messageObj.hasOwnProperty("direction")) {
      messageObj.direction = "right";
      socket.sendMessage(messageObj);
    }
  }

  if (!keyMap[this.leftKey]) {
    if (!messageObj.hasOwnProperty("direction")) {
      messageObj.direction = "stop";
      socket.sendMessage(messageObj);
    }
  }
  if (!keyMap[this.rightKey]) {
    if (!messageObj.hasOwnProperty("direction")) {
      messageObj.direction = "stop";
      socket.sendMessage(messageObj);
    }
  }
};

var ip = 'x.x.x.x'; //voor later
var port = ':8080';

var init = function() {
  var fireModal = function() {
    $("#selectController").modal().modal('open');;
    var cols = 12;
    var players = prompt("How many playersssss?");
    var grootteCols = (cols / players);
    var currentslot = 1;
    while (currentslot <= players) {
      $(".modal-content").append("<div class='controllercol center col s" + grootteCols + "'>" + currentslot + " player(s) <br/><a class='dropdown-button btn' href='#' data-activates='dropdown1'>Drop Me!</a><ul id='dropdown1' class='dropdown-content'><li><a href='#!'><i class='material-icons'>keyboard</i>keys</a></li><li><a href='#!'><i class='material-icons'>phone_iphone</i>phone</a></li></ul></div>");
      console.log("new slot");
      currentslot += 1;
    }
  };
  return {fireModal};
}();
var input = function() {
  // Private
  var keyMap = {};
  onkeydown = onkeyup = function(e) {
    e = e || event; // damn you IE..
    keyMap[e.keyCode] = e.type === 'keydown';

    for (var player in players) {
      players[player].move(keyMap);
    }
  }
  // Public
  var players = [];
  return {players};
}();
var comms = function() {
  // Private
  var gameInterval = null;
  var infoInterval = null;
  var getGameInfo = function() {
    var messageObj = {type: "gameInfo"};
    socket.sendMessage(messageObj);
  };
  var getPosistion = function() {
    var messageObj = {type: "updateMe"};
    socket.sendMessage(messageObj);
  };
  // Public
  var startGame = function() {
    //$("#selectController").hide();
    var playerAmount = prompt("How many players");
    for (var i = 0; i < parseInt(playerAmount); i++) {
      var leftKeyCode = parseInt(prompt("left Key:").charCodeAt(0)-32); // TODO: move to a seperate fucntion perhaps?
      var rightKeyCode = parseInt(prompt("right key:").charCodeAt(0)-32); // #readability
      var abilityKeyCode = parseInt(prompt("ability").charCodeAt(0)-32);
      input.players.push(new Player(leftKeyCode, rightKeyCode, abilityKeyCode, ""+(i+1)));
    }
    var messageObj = {type: "startGame", playerAmount};
    socket.sendMessage(messageObj);
    getUpdate();
  };
  var getUpdate = function() {
    gameInterval = setInterval(getPosistion, 15);
    infoInterval = setInterval(getGameInfo, 50);
  };
  var stopUpdates = function() {
    clearInterval(gameInterval);
    clearInterval(infoInterval);
  };
  return {startGame, getUpdate, stopUpdates};
}();
var gui = function() {
  var drawFromPosistion = function(message) {
    const posArray = message;
    bricks = [];
    for (var sprite in posArray) {
      var oneSprite = posArray[sprite];
      switch (oneSprite.type) {
        case "Pallet":
          pallet = new Pallet(oneSprite.x, oneSprite.y, oneSprite.width, oneSprite.height, imgPallet);
          break;
        case "Ball":
          ball = new Ball(oneSprite.radius, oneSprite.x, oneSprite.y, imgBall); // TODO: Move this to seperate functions?
          break;
        case "Brick":
          bricks.push(new Brick(oneSprite.x, oneSprite.y, oneSprite.width, oneSprite.height, getImage(oneSprite.color)));
          break;
      }
    }
  };
  var gameInfo = function(player) {
    var lives = player.lives;
    var score = player.score;
  };
  return {drawFromPosistion, gameInfo};
}();
var socket = function() {
  // Private
  var url = "ws://localhost:8080/Project_Breakout/gamepoint";
  var socket = new WebSocket(url);
  socket.onopen = function() {
    //socket.sendMessage(JSON.stringify({"flppn": 3}));
  };
  socket.onmessage = function(messageRecieved) {
    var message = JSON.parse(messageRecieved.data);
    switch (message.type) {
      case "posistion":
        gui.drawFromPosistion(message);
        break;
      case "gameInfo":
        gui.gameInfo(message);
        break;
    }
  };
  // Public
  function sendMessage(message) {
    socket.send(JSON.stringify(message));
  }
  return {sendMessage};
}();

// DRAW FUNCTIONS (P5.JS) //
var ball = null; // TODO: SOOO MANYY GLOBALS ;-;
var pallet = null;
var imgBall = null;
var imgPallet = null;
var bricks = [];
var blockImages = {};
var preload = function() { // TODO: could this be done better???
  imgPallet = loadImage('assets/media/pallet.png');
  imgBall = loadImage('assets/media/ball.png');
  blockImages.black = loadImage('assets/media/black_block.png');
  blockImages.green = loadImage('assets/media/green_block.png');
  blockImages.purple = loadImage('assets/media/purple_block.png');
  blockImages.red = loadImage('assets/media/red_block.png');
  blockImages.yellow = loadImage('assets/media/yellow_block.png');
};
function getImage(color) { // TODO: pls let there be a way to do this better
  switch (color) {
    case "yellow":
      return blockImages.yellow;
      break;
    case "blue":
      return blockImages.black; // TODO: Actually make this be the blue blocks..
      break;
    case "purple":
      return blockImages.purple;
      break;
    case "red":
      return blockImages.red;
      break;
    case "green":
      return blockImages.green;
      break;
    default:
      return blockImages.green;
      break;
  }
}
function setup() {
  var canvas = createCanvas(750, 400);
  canvas.parent('game-area');
}
function draw() {
  var check = ball !== null && pallet !== null;
  console.log(check); // TODO: remove this in final version, also move the boolean check to the if then
  if (check) {
    background(47, 49, 54);
    ball.show();
    pallet.show();
    for (var i = 0; i < bricks.length; i++) {
      bricks[i].show();
    }
  }
}

$(document).ready(function() {
  console.log("game.js is loaded");
  init.fireModal();
  $(".startGame").on("click", comms.startGame);
});
