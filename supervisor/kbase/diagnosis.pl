
diag(failure(x10_failure, _TimeStamp, _Description, _States, _Goals)):-
    Sequence=[
        (    true,         assert(action(move_x_left))  ),
        (    x_is_at(_),   assert(action(stop_x))      )
    ],
    new_id(ID),
    assert(sequence(ID, recovery_x_10, Sequence)),
    assert(plan_to_json(Sequence)).


diag(failure(x1_failure, _TimeStamp, _Description, _States, _Goals)):-
    Sequence=[
        (   true, assert(action(move_x_right))),
        (   x_is_at(_), assert(action(stop_x)))
    ],
    new_id(ID),
    assert(sequence(ID,recovery_x_1,Sequence)),
    assert(plan_to_json(Sequence)).

diag(failure(z1_failure, _TimeStamp, _Description, _States, _Goals)):-
    Sequence=[
        (   true, assert(action(move_z_up))),
        (   z_is_at(_), assert(action(stop_z)))
    ],
    new_id(ID),
    assert(sequence(ID,recovery_z_1,Sequence)),
    assert(plan_to_json(Sequence)).

diag(failure(z5_failure, _TimeStamp, _Description, _States, _Goals)):-
    Sequence=[
        (   true, assert(action(move_z_down))),
        (   z_is_at(_), assert(action(stop_z)))
    ],
    new_id(ID),
    assert(sequence(ID,recovery_z_5,Sequence)),
    assert(plan_to_json(Sequence)).

diag(failure(y1_failure, _TimeStamp, _Description, _States, _Goals)):-
    Sequence=[
        (   true, assert(action(move_y_inside))),
        (   y_is_at(_), assert(action(stop_y)))
    ],
    new_id(ID),
    assert(sequence(ID,recovery_y_1,Sequence)),
    assert(plan_to_json(Sequence)).

diag(failure(y3_failure, _TimeStamp, _Description, _States, _Goals)):-
    Sequence=[
        (   true, assert(action(move_y_outside))),
        (   y_is_at(_), assert(action(stop_y)))
    ],
    new_id(ID),
    assert(sequence(ID,recovery_y_3,Sequence)),
    assert(plan_to_json(Sequence)).

diag(failure(no_part_station_failure, _TimeStamp, _Description, _States, _Goals)):-
    Sequence=[
        (   true, assert(action(stop_z))),
        (   z_moving(0), assert(action(move_y_inside))),
        (   y_is_at(2), assert(action(stop_y)))
    ],
    new_id(ID),
    assert(sequence(ID,recovery_no_part_station,Sequence)),

    retract(cage(_)),
    assert(plan_to_json(Sequence)).
/*
diag(failure(empty_cage_failure, _TimeStamp, _Description, _States, _Goals)):-
    Sequence=[
        (   true, assert(goto_y(1)))
    ],
    new_id(ID),
    assert(sequence(ID,recovery_empty_cage,Sequence)),
    assert(plan_to_json(Sequence)).
*/









