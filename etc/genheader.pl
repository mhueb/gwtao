use Getopt::Long;
use Cwd;

print( "\generate file header comment\n" );

$ret = GetOptions ( 'Help!', \$Help, 'C:s', \$icopyright, 'P:s', \$iproject, 'R!', \$rekursiv, 'L:s', \$ilicense);
($ret) or die "invalid arguments. -help for help\n";

if( $Help ) {
	print( "genheader [-R] [-C Copyright] [-P Projekt] [-E Name] \n" );
	print( "-R            recurse into directoy\n" );
	print( "-C Copyright  Copyright info\n" );
	print( "-P Projekt    Project name\n" );
	print( "-L license    license note, LGPL for LGPL notice\n" );
	print( "\n" );
	exit(0);
}

$d = d;
$CurrentDir = `cd`;
$CurrentDir =~ tr/\\/\//;
chomp $CurrentDir;

$root = getcwd();
$root=~s/\\/\//;

processdir( $root );

sub processdir( ) {
	my $dir = $_[0];
	my $file;
	
	opendir( DIR, "$dir" ) or die "directory $dir not found\n";
	my @dircontent = readdir( DIR );
	closedir( DIR );

	chdir( $dir );
	
	foreach $file ( sort @dircontent ) {
		chomp $file; # strip the linefeed

		if( $file =~ /^(.+\.java)$/ ) {
			processfile( $dir, $1 );
		}
		elsif( -d $file && ! ($file eq '.') && ! ($file eq '..') && !( $file =~ /\.(__new|__bak)$/) && $rekursiv ) {
			processdir( "$dir/$file" );
			chdir( $dir );
		}
	}
}

sub processfile( ) {
	my $filePath = $_[0];
	my $file = $_[1];

	open( INFILE, "$filePath/$file" );

	unlink( "$filePath/$file.__new" );
	open( OUTPUT,  ">$filePath/$file.__new" );

	$IsChanged = 0;
	$author="";
	$date="";
	$rev="";
	$lastline="";
	$project = "";
	$copyright = "";
	$idline = "";

	while( $line = <INFILE> ) {
		chomp $line;	# strip the linefeed
		next if( length( $line ) == 0 );

		if( $line =~ /^\/\*/ ) {
			$block=1;
		}

		if( $block ) {
			if( $line =~ /\*\// ) {
				$block=0;
				$comment_block = 0;
				next;
			}

			$IsChanged = 1;

			if( $line =~ /^\s*[\*]?\s*\$Author\s*\:\s*([^\$]*)\$\s*$/ ) {
				$author = $1;
				$comment_block = 0;
			}
			elsif( $line =~ /^\s*[\*]?\s*\$Date\s*\:\s*([^\$]*)\$\s*$/ ) {
				$date = $1;
				$comment_block = 0;
			}
			elsif( $line =~ /^\s*[\*]?\s*\$Revision\s*\:\s*([^\$]*)\$\s*$/ ) {
				$rev = $1;
				$comment_block = 0;
			}
			elsif( $line =~ /^\s*[\*]?\s*\$Id\s*\:\s*([^\$]*)\$\s*$/ ) {
				$idline = $1;
				$comment_block = 0;
			}
			elsif( $line =~ /^\s*[\*]?\s*[\$]?\w+\s*\:\s*.*$/ ) {
				$comment_block = 0;
			}
			elsif( $comment_block ) {
				if( $line =~ /^\s*[\*]?\s*(.*)$/ ) {
					$comment = "$comment\n//                 $1";
				}
				else {
					$comment_block = 0;
				}
			}

			next;
		}

		$lastline=$line;
		$IsChanged = 1;
		last;
	}

	if( $IsChanged ) {
		if( $author =~ /(.+)\s+$/ ) { $author=$1; }
		if( $author =~ /\s*(.+)$/ ) { $author=$1; }
		if( $date =~ /(.+)\s+$/ ) { $date=$1; }
		if( $date =~ /\s*(.+)$/ ) { $date=$1; }
		if( $rev =~ /(.+)\s+$/ ) { $rev=$1; }
		if( $rev =~ /\s*(.+)$/ ) { $rev=$1; }

		if( $iproject ) { $project = $iproject; }
		if( $icopyright ) { $copyright = $icopyright; }
		if( $ilicense ) { $license = $ilicense; }

		$XAUT= "Author";
		$XDAT= "Date";
		$XREV= "Revision";
		$XID="Id";

		print OUTPUT "/* \n";
		
		if( $project ) {
			print OUTPUT " * $project\n * \n"; 
		}

		if( $copyright ) {
			print OUTPUT " * $copyright\n * \n"; 
		}

		if( $license ) {
			if( $license =~ /LGPL/ ) {
				print OUTPUT " * This is free software; you can redistribute it and/or modify it\n";
				print OUTPUT " * under the terms of the GNU Lesser General Public License as\n";
				print OUTPUT " * published by the Free Software Foundation; either version 3 of\n";
				print OUTPUT " * the License, or (at your option) any later version.\n";
				print OUTPUT " *\n";
				print OUTPUT " * This software is distributed in the hope that it will be useful,\n";
				print OUTPUT " * but WITHOUT ANY WARRANTY; without even the implied warranty of\n";
				print OUTPUT " * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU\n";
				print OUTPUT " * Lesser General Public License for more details.\n";
				print OUTPUT " *\n";
				print OUTPUT " * You should have received a copy of the GNU Lesser General Public\n";
				print OUTPUT " * License along with this software; if not, write to the Free\n";
				print OUTPUT " * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA\n";
				print OUTPUT " * 02110-1301 USA, or see the FSF site: http://www.fsf.org.\n *\n";
			}
			else {
				print OUTPUT " * $license\n * \n"; 
			}
		}

		# print OUTPUT " * \$$XID: $idline\$\n";
		print OUTPUT " */\n";

		if( $lastline ) {
			print OUTPUT "$lastline\n";
		}
	}

	while( $line = <INFILE> ) {
		print OUTPUT $line;
	}

	close INFILE;
	close OUTPUT;

	if( $IsChanged == 1 ) {
		open( IN1, "$filePath/$file" );
		open( IN2,  "$filePath/$file.__new" );

		$IsChanged = 0;

		while( $a = <IN1> ) {
			$b = <IN2>;

			chomp($a);
			chomp($b);

			next if( $a eq $b );
			
			$IsChanged = 1;
			last;
		}

		close IN1;
		close IN2;
	}

	if( $IsChanged == 1 ) {
		print( "$filePath/$file changed.\n" );

		chmod 0666, "$filePath/$file.__new";
		chmod 0666, "$filePath/$file.__bak";
		chmod 0666, "$filePath/$file";

		if( -d "$filePath/$file.__bak" && !unlink( "$filePath/$file.__bak" ) ) {
			print( "  failed to delete $filePath/$file.__bak\n" ); 
		}
		
		if( !rename( "$filePath/$file" , "$filePath/$file.__bak" ) ) {
			print( "  failed to delete $filePath/$file.__bak\n" ); 
		}
		
		if( !rename( "$filePath/$file.__new" , "$filePath/$file" ) ) { 
			print( "  failed to delete $filePath/$file\n" ); 
		}
	}
	else {
		unlink( "$filePath/$file.new" );
	}
}
