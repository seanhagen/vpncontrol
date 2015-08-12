# vpncontrol

This is an app I built that can control the VPN clients configured in a Tomato-based router.

My goal is that this will end up being a stand-alone app that presents a web interface super simple. I wanted something that would let my girlfriend turn the VPN on/off ( mostly for Netflix purposes ) without having to know how to deal with the Tomato web interface.

## Usage

Before you do any of this, create an SSH key just for your router. If you don't know how, [GitHub has a great guide on how to do that](https://help.github.com/articles/generating-ssh-keys/).

### Router Setup

First, log in to your Tomato router, and then go to the 'Administration' menu.

Once there, scroll down to the _SSH Daemon_ section. Click the checkbox next to `Enable at Startup`. Make sure the checkboxes next to `Enable MOTD` and `Allow Password Login` are turned off.

Open up the public portion of the SSH key you created ( the filename that ends in `.pub` ), and copy and paste _all_ of the text into the text box next to `Authorized Keys`.

Scroll up to the _Web Admin_ section ( it should be at the top of the page ). Make sure the drop-down next to `Local Access` is set to __HTTP__, and that the `HTTP Port` is set to __80__.

### App Setup

First, rename `config.clj.example` to `config.clj`. Then open it up in your favorite text editor. You'll need to update the following values:

- __ssh-key__: should be a path that points to an SSH key that you've created.
- __ssh-host__: should be the IP address for your router
- __api-user__: should be `admin`
- __api-password__: set this to whatever password your set for your `admin` user

Once you've done that, you're ready to run!

    $ java -jar vpncontrol-0.1.0-standalone.jar [args]

## Bugs

None Yet.

## Libraries

These are the Clojure libraries in use by this project:

- [clj-http](https://github.com/dakrone/clj-http)
- [clj-ssh](https://github.com/hugoduncan/clj-ssh)

## License

Copyright © 2015 Sean Hagen

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
