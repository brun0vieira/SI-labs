:-ensure_loaded('RTXengine/RTXutil.pl').
:-ensure_loaded('RTXengine/RTXengine.pl').


:-dynamic x_before/1.

/*
monitoring:-
    x_is_at(X),
    \+ x_before(X),
    assert_once(x_before(X)).

monitoring:-
    \+ x_is_at(_),
    x_before(X),
    x_moving(1),
    X_between is X-0.5,
    assert_once(x_is_near(X_between)).

monitoring:-
    \+ x_is_at(_),
    x_before(X),
    x_moving(-1),
    X_between is X-0.5,
    assert_once(x_is_near(X_between)).
*/
