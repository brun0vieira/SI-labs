recover_failure:-
    % dummy recovery, use if it suits you
    write('recovering a failure'),
    retract(failures_to_json(_)),
    !.

% other recovery failures here...

recover_failure. % this should be the last one
