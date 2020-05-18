# ExoPlayer without configChange flag
Sample implementation showing ExoPlayer handling configuration changes (orientation change etc.) without configChange flag set to true.

This implementation puts ExoPlayer and its player view into a Fragment and retains fragment instance so fragment doesn't get destroyed 
during the config changes. Even if the fragment instance is retained, its view is always destroyed when the parent activity is destroyed, so we create a new player view and attach
retained player instance to new player view every time it's destroyed.




