let ageDiffer = $('#mainForm\\:ageDiff').text();
console.log(ageDiffer);

if (ageDiffer === 0) {
    $('#mainForm\\:sameAge').css({'display': 'inline'});
}

else{
    console.log($('#mainForm\\:absAgeDiff').text());
    $('#mainForm\\:absAgeDiff').css({'display': 'inline'});
    
    if($('#mainForm\\:absAgeDiff').text() > 1) {
        $('#mainForm\\:years').css({'display': 'inline'});
    }
    else {
        $('#mainForm\\:year').css({'display': 'inline'});
    }
    
    if (ageDiffer < 0) {
        $('#mainForm\\:younger').css({'display': 'inline'});
    }
    else {
        $('#mainForm\\:older').css({'display': 'inline'});
    }
}