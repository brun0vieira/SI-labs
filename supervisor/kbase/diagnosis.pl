diag(failure(x10_failure, _TimeStamp, _Descripton, _States, _Goals)):-
    Sequence=[
        (    true,         assert(action(move_x_left))  ),
        (    x_is_at(_),   assert(action(stop_x))      )
    ],
    new_id(ID),
    assert(sequence(ID, recovery_x_10, Sequence)),
    assert(plan_to_json(Sequence)).



recover_failure:-
    % dummy recovery, use if it suits you
    write('recovering a failure'),
    retract(failures_to_json(_)),
    !.

% other recovery failures here...

recover_failure. % this should be the last one
