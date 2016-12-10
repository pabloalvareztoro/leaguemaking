var express = require('express');
var router = express.Router();

router.post('/', function(req, res, next) {
  shuffleArray(req.body);
  balanceFixture(req.body);
  res.json(createFixture(req.body));
});

function createFixture(body) {
  var fixture = createFirstSemestre(body);
  fixture = fixture.concat(createSecondSemestre(fixture))
  return fixture;
}

function createFirstSemestre(body){
    var fixture = [];
    for(var i=0; i < body.length-1; i++){
        fixture[i] = [];
        for(var j=0; j < body.length/2; j++){
            if((body[j] != null) && (body[body.length-j-1] != null)){
                if(isOdd(i)){
                    if(body[j], body[body.length-j-1] != null)
                        fixture[i].push([body[j], body[body.length-j-1]])
                }else{
                    fixture[i].push([body[body.length-j-1], body[j]])
                }
            }
        }
        shuffleArray(fixture[i]);
        var first = body.shift();
        var shifted = body.pop();
        body.unshift(shifted);
        body.unshift(first);
    }
    return fixture;
}

function createSecondSemestre(fixture){
    var newFixture = [];
    for(var i=0; i < fixture.length; i++){
        newFixture[i] = [];
        for(var j=0; j < fixture[i].length; j++){
            newFixture[i].push([fixture[i][j][1], fixture[i][j][0]]);
        }
    }
    return newFixture;
}

var balanceFixture = function(teams){
    if(isOdd(teams.length)){
        teams.push(null);
    }
}

var isOdd = function(x) { return x & 1; };

/**
 * Randomize array element order in-place.
 * Using Durstenfeld shuffle algorithm.
 */
function shuffleArray(array) {
    for (var i = array.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    return array;
}

module.exports = router;
