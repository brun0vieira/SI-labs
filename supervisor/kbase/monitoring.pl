:-ensure_loaded('RTXengine/RTXutil.pl').
:-ensure_loaded('RTXengine/RTXengine.pl').


:-dynamic x_before/1, y_before/1, z_before/1.
:-dynamic x_is_near/1, y_is_near/1, z_is_near/1.
:-dynamic cell/3, cage/1.


defrule x_position_estimation
     if x_is_at(X) and x_moving(Mov) and (Mov\==0)
     then[
        X_near is X+0.5*Mov,
        assert_once(x_is_near(X_near)),
        assert_once(x_before(X))
     ].

defrule z_position_estimation
     if z_is_at(Z) and z_moving(Mov) and (Mov\==0)
     then[
        Z_near is Z+0.5*Mov,
        assert_once(z_is_near(Z_near)),
        assert_once(z_before(Z))
     ].

defrule y_position_estimation
     if y_is_at(Y) and y_moving(Mov) and (Mov\==0)
     then[
        Y_near is Y+0.5*Mov,
        assert_once(y_is_near(Y_near)),
        assert_once(y_before(Y))
     ].

defrule time_of_event_x_is_at
     if x_is_at(X) and not(time(x_is_at(_), _))
     then [
        retractall(time(x_is_at(_), _) ),
        get_time(Time),
        assert(time(x_is_at(X),Time))
     ].

defrule time_of_event_z_is_at
     if z_is_at(Z) and not(time(z_is_at(_), _))
     then [
        retractall(time(z_is_at(_), _) ),
        get_time(Time),
        assert(time(z_is_at(Z),Time))
     ].

defrule time_of_event_y_is_at
     if y_is_at(Y) and not(time(y_is_at(_), _))
     then [
        retractall(time(y_is_at(_), _) ),
        get_time(Time),
        assert(time(y_is_at(Y),Time))
     ].

%test if actuador xx is moving past x=10
defrule beyond_last_sensor_error_x
     if goal(action(move_x_right))
          and not(failure(x10_failure,_,_,_,_))    %avoid avalancche of failure facts
          and not(x_is_at(_))
          and x_is_near(X)
          and (X>10)
          and x_moving(1)
     then [           %adjust the time aqccording to the simulator speed
          get_warehouse_states(States),
          findall( goal(G), goal(G), Goals),
          get_time(Time_now),
          assert(failure(x10_failure, Time_now, 'Moving beyond x=10', States, Goals))
          %,writeq(failure(x10_failure, Time_now, 'Moving beyond x=10', States, Goals))
     ].

% SPECIFY ALL THE OTHER RULES HERE

%test if actuator xx is moving past x=1
defrule beyond_first_sensor_error_x
     if goal(action(move_x_left))
        and not(failure(x1_failure,_,_,_,_))
        and not(x_is_at(_))
        and x_is_near(X)
        and (X<1)
        and x_moving(-1) % x_moving(X), X equals -1 if it's moving to the left
     then [
         get_warehouse_states(States),
         findall(goal(G),goal(G),Goals),
         get_time(Time_now),
         assert(failure(x1_failure,Time_now,'Moving beyond x=1',States,Goals))
         %,writeq(failure(x1_failure,Time_now,'Moving beyond x=1',States,Goals))
     ].

%test if actuator zz is moving past x=5
defrule beyond_last_sensor_error_z
     if goal(action(move_z_up))
         and not(failure(z5_failure,_,_,_,_))
         and not(z_is_at(_))
         and z_is_near(Z)
         and (Z>5)
         and z_moving(1)
     then [
         get_warehouse_states(States),
         findall(goal(G),goal(G),Goals),
         get_time(Time_now),
         assert(failure(z5_failure,Time_now,'Moving beyond z=5',States,Goals))
         %,writeq(failure(z5_failure,Time_now,'Moving beyond z=5',States,Goals))
     ].

%test if actuator zz is moving past z=1
defrule beyond_first_sensor_error_z
     if goal(action(move_z_down))
         and not(failure(z1_failure,_,_,_,_))
         and not(z_is_at(_))
         and z_is_near(Z)
         and (Z<1)
         and z_moving(-1)
     then [
         get_warehouse_states(States),
         findall(goal(G),goal(G),Goals),
         get_time(Time_now),
         assert(failure(z1_failure,Time_now,'Moving beyond z=1',States,Goals))
         %,writeq(failure(z1_failure,Time_now,'Moving beyond z=1',States,Goals))
     ].

%test if actuator yy is moving past y=3
defrule beyond_last_sensor_error_y
     if goal(action(move_y_inside))
         and not(failure(y3_failure,_,_,_,_))
         and not(y_is_at(_))
         and y_is_near(Y)
         and (Y>3)
         and y_moving(1)
     then [
         get_warehouse_states(States),
         findall(goal(G),goal(G),Goals),
         get_time(Time_now),
         assert(failure(y3_failure,Time_now,'Moving beyond y=3',States,Goals))
         %,writeq(failure(y3_failure,Time_now,'Moving beyond y=3',States,Goals))
     ].

%test if actuator yy is moving past y=1
defrule beyond_first_sensor_error_y
     if goal(action(move_y_outside))
         and not(failure(y1_failure,_,_,_,_))
         and not(y_is_at(_))
         and y_is_near(Y)
         and (Y<1)
         and y_moving(-1)
     then [
         get_warehouse_states(States),
         findall(goal(G),goal(G),Goals),
         get_time(Time_now),
         assert(failure(y1_failure,Time_now,'Moving beyond y=1',States,Goals))
         %,writeq(failure(y1_failure,Time_now,'Moving beyond y=1',States,Goals))
     ].

defrule loading_to_lift_no_part_left
     if monitoring_left_part(Block)
        and not(failure(no_part_station_failure,_,_,_,_))
        and not(is_part_at_left_station)
     then [
         get_warehouse_states(States),
         retract_safe(monitoring_left_part(Block)),
         findall(goal(G),goal(G),Goals),
         get_time(Time_now),
         assert(failure(no_part_station_failure,Time_now,'Loading into lift without part at left station',States,Goals))
         %,writeq(failure(no_part_station_failure,Time_now,'Loading into lift without part at left station',States,Goals))
     ].

defrule loading_to_lift_no_part_right
     if monitoring_right_part(Block)
        and not(failure(no_part_station_failure,_,_,_,_))
        and not(is_part_at_right_station)
     then [
         get_warehouse_states(States),
         retract_safe(monitoring_right_part(Block)),
         findall(goal(G),goal(G),Goals),
         get_time(Time_now),
         assert(failure(no_part_station_failure,Time_now,'Loading into lift without part at right station',States,Goals))
         %,writeq(failure(no_part_station_failure,Time_now,'Loading into lift without part at right station',States,Goals))
     ].

defrule give_left_station_occupied
    if monitoring_give_lstation(Block)
       and not(failure(left_station_occupied_failure,_,_,_,_))
       and is_part_at_left_station
    then [
        assert(cage(Block)),
        get_warehouse_states(States),
        retract_safe(monitoring_give_lstation(Block)),
        findall(goal(G),goal(G),Goals),
        get_time(Time_now),
        assert(failure(left_station_occupied_failure,Time_now,'The left station is occupied',States,Goals))
        %,writeq(failure(left_station_occupied_failure,Time_now,'The left station is occupied',States,Goals))
    ].

defrule give_right_station_occupied
    if monitoring_give_rstation(Block)
       and not(failure(right_station_occupied_failure,_,_,_,_))
       and is_part_at_right_station
    then [
        assert(cage(Block)),
        get_warehouse_states(States),
        retract_safe(monitoring_give_rstation(Block)),
        findall(goal(G),goal(G),Goals),
        get_time(Time_now),
        assert(failure(right_station_occupied_failure,Time_now,'The right station is occupied',States,Goals))
        %,writeq(failure(right_station_occupied_failure,Time_now,'The right station is occupied',States,Goals))
    ].


/*defrule loading_to_lift_empty_cage_left
     if monitoring_empty_cage(Block)
         and not(failure(empty_cage_failure, , ,_,_))
         and not(cage_has_part)
     then [
         get_warehouse_states(States),
         retract_safe(monitoring_empty_cage(Block)),
         findall(goal(G),goal(G),Goals),
         get_time(Time_now),
         assert(failure(empty_cage_failure,Time_now,'Empty lift after load from station',States,Goals))
         %,writeq(failure(empty_cage_failure,Time_now,'Empty lift after load from station'',States,Goals))
     ].
*/

defrule diagnose_failures_rule
     if failure(Type, TimeStamp, Description, States, Goals)
     then[
         Failure = failure(Type, TimeStamp, Description, States, Goals),
         findall( _Ignore1 ,                                                                         diag(Failure), % <-- CALLING DIAGNOSIS
            _Ignore2),
         retract(Failure),
         % save the failure to be shown in the html UI-console(NEXT CLASS)
         %assert(failures_to_json(Failure)),
         assert_once(failures_to_json(Failure)),
         writeln(Failure)
      ].

/*
save_management_states:-
    tell('kbase/management_states.pl'),
    listing(x_before),
    listing(x_is_near),
    listing(y_before),
    listing(y_is_near),
    listing(z_before),
    listing(z_is_near),
    listing(cell),
    listing(cage),
    told.
*/









